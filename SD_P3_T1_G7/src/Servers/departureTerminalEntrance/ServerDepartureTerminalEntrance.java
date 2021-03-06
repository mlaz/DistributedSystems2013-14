package Servers.departureTerminalEntrance;

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
 * This class establishes a new service, the Departure Terminal Entrance.
 * The communications use RMI.
 * 
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 */
public class ServerDepartureTerminalEntrance {
	/**
	 * The port this service will use
	 */
	private static final int portNumber = 22166;
	/**
	 * How to launch this service
	 */
	private static final String usage = "Usage: java -jar RMIDepartureTerminalEntrance [genRepRegistryName]";

    
    /**
     * Starts the server
     * @param args Only one argument is allowed. The name of the server hosting the General Repository
     */
    public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println(usage);
			args = new String[1];
			args[0] = "localhost";
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
		int numPassengers = 0;
		try {
			genRep = (IGenRep) genRepRegistry.lookup(RmiUtils.genRepId);
			numPassengers = genRep.getNumPassengers();
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
		
		/* establecer o serviço */
		MDepartureTerminalEntrance DepartureTerminalEntrance = new MDepartureTerminalEntrance(numPassengers, numPassengers + 2);
		IDepartureTerminalEntrance DepartureTerminalEntranceInter = null;
		try {
			DepartureTerminalEntranceInter = (IDepartureTerminalEntrance) UnicastRemoteObject.exportObject(DepartureTerminalEntrance, portNumber);
		} catch (RemoteException e) {
			System.err.println("Error creating the DepartureTerminalEntrace stub");
			e.printStackTrace();
			System.exit(1);
		}
		
		System.out.println( "Departure Terminal Entrance stub created" );
		
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
			registry.bind(RmiUtils.departureTerminalEntraceZoneId, DepartureTerminalEntranceInter);
		} catch (RemoteException | AlreadyBoundException e) {
			System.err.println("Error binding the DepartureTerminalEntrace to the RMI registry");
			e.printStackTrace();
			System.exit(1);
		}
		
        System.out.println("Departure Terminal Entrance binded to RMI registry (port "+portNumber+")");
        
        try {
			genRep.registerService(RmiUtils.departureTerminalEntraceZoneId, InetAddress.getLocalHost().getHostName());
		} catch (RemoteException | UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        System.out.println("Ready");

	}
	
}