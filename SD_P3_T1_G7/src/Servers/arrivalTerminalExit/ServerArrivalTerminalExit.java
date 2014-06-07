package Servers.arrivalTerminalExit;

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
public class ServerArrivalTerminalExit {

	private static final int portNumber = 22162;
	private static final String usage 	= "Usage: java -jar RMIArrivalTerminalExit [genRepRegistryName]";

    /**
     *
     * @param args
     */
    public static void main(String[] args) {

		if (args.length != 2) {
			System.out.println(usage);
			// System.exit(1);
			args = new String[2];
			args[0] = "localhost";
			args[1] = "22168";
		}

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
		
		/* obter parametros do problema */
		int numFlights = 0;
		int numPassengers = 0;
		int numSeats = 0;
		try {
			numFlights 	  = genRep.getNumFlights();
			numPassengers = genRep.getNumPassengers();
			numSeats 	  = genRep.getNumBusSeats();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/* establecer o serviço */
		MArrivalTerminalExit arrivalTerminalExit = new MArrivalTerminalExit(numFlights, numPassengers, numSeats, genRep);
		IArrivalTerminalExit arrivalTerminalExitInter = null;
		try {
			arrivalTerminalExitInter = (IArrivalTerminalExit) UnicastRemoteObject.exportObject(arrivalTerminalExit, portNumber);
		} catch (RemoteException e) {
			System.err.println("Error creating the ArrivalTerminalExit stub");
			e.printStackTrace();
			System.exit(1);
		}
		
		System.out.println("Arrival Terminal Exit stub created");
		
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
			registry.bind(RmiUtils.arrivalTerminalTransferZoneId, arrivalTerminalExitInter);
		} catch (RemoteException | AlreadyBoundException e) {
			System.err.println("Error binding the ArrivalTermminalExit to the RMI registry");
			e.printStackTrace();
			System.exit(1);
		}
		
        System.out.println("Arrival Terminal Exit binded to RMI registry (port " + portNumber + ")");
        try {
			genRep.registerService(RmiUtils.arrivalTerminalTransferZoneId, InetAddress.getLocalHost().getHostName());
		} catch (RemoteException | UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Ready");			

	}
}