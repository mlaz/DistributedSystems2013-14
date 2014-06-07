package Servers.arrivalTerminal;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import Servers.genRep.IGenRep;
import Utils.RmiUtils;

/**
 * Classe de servidor com replicação para receção de pedidos ao monior por parte das threads(clientes)
 * @author miguel
 */
public class ServerArrivalTerminal {
	private static int portNumber = 22161;
	private static String usage = "Usage: java -jar RMIArrivalTerminal [genRepRegistryName]";
	
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
		
		if (args.length != 1) {
			System.out.println(usage);
			// System.exit(1);
			args = new String[1];
			args[0] = "localhost";
		}
		/* obter parametros do problema */
		
		Registry genRepRegistry = null;
		try {
			genRepRegistry = LocateRegistry.getRegistry(args[0], RmiUtils.rmiPort);
		} catch( RemoteException e ) {
			System.err.println( "Error accessing the RMI registry: " + e.getMessage() );
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println( "GenRep RMI registry accessed" );
		
		IGenRep genRep = null;
		try {
			genRep = (IGenRep) genRepRegistry.lookup(RmiUtils.genRepId);
		} catch (AccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.exit(1);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.exit(1);
		} catch (NotBoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.exit(1);
		}
		
		System.out.println("GenRep accessed");
		
		int numFlights = 0;
		int numPassengers = 0;
		int maxBags = 0;	
		try {
			numFlights 	  = genRep.getNumFlights();
			numPassengers = genRep.getNumPassengers();
			maxBags		  = genRep.getMaxBags();	
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		/* establecer o serviço */
		MArrivalTerminal arrivalTerminal = new MArrivalTerminal(numFlights, numPassengers, maxBags);
		IArrivalTerminal arrivalTerminalInter = null;
		try {
			arrivalTerminalInter = (IArrivalTerminal) UnicastRemoteObject.exportObject(arrivalTerminal, portNumber);
		} catch (RemoteException e) {
			System.err.println("Error creating the ArrivalTerminal stub");
			e.printStackTrace();
			System.exit(1);
		}
		
		System.out.println("Arrival Termina stub created");
		
		Registry registry = null;
		try {
			registry = LocateRegistry.getRegistry(RmiUtils.rmiPort);
		} catch( RemoteException e ) {
			System.err.println( "Error accessing the RMI registry: " + e.getMessage() );
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println( "Local RMI registry accessed" );
		
		try {
			registry.bind(RmiUtils.arrivalTerminalId, arrivalTerminalInter);
		} catch (RemoteException | AlreadyBoundException e) {
			System.err.println("Error binding the ArrivalTermminal to the RMI registry");
			e.printStackTrace();
			System.exit(1);
		}
        System.out.println("Arrival Terminal binded to RMI registry (port " + portNumber + ")");
        
        try {
			genRep.registerService(RmiUtils.arrivalTerminalId, InetAddress.getLocalHost().getHostName());
		} catch (RemoteException | UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Ready");
	}

}