package Servers.arrivalTerminal;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
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
//	private static String hostName;
//	private static ServerInfo genRepInfo;
	private static String usage = "Usage: java ServerArrivalTerminal [thisMachineName] [genRepName] [genRepPort]";
	
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
		
		if (args.length != 3) {
			System.out.println(usage);
			// System.exit(1);
			args = new String[3];
			args[0] = "localhost";
			args[1] = "localhost";
			args[2] = "22160";
		}
		/* obter parametros do problema */
		
		/* get the RMI registry */
		Registry rmiReg = null;
		try {
			rmiReg = RmiUtils.getRMIReg( args[1], Integer.parseInt(args[2]), usage );
		} catch (NumberFormatException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (RemoteException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		System.out.println("RMI registry located!");
		
		IGenRep genRep = null;
		try {
			genRep = (IGenRep) rmiReg.lookup(RmiUtils.genRepId);
		} catch (AccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NotBoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		int numFlights = 0;
		int numPassengers = 0;
		int maxBags = 0;	
		try {
			numFlights = genRep.getNumFlights();
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
		
		try {
			rmiReg.bind(RmiUtils.arrivalTerminalId, arrivalTerminalInter);
		} catch (RemoteException | AlreadyBoundException e) {
			System.err.println("Error binding the ArrivalTermminal to the RMI registry");
			e.printStackTrace();
			System.exit(1);
		}
		
        System.out.println("Arrival Terminal service is listening on port " + portNumber + "...");
        
	}

}