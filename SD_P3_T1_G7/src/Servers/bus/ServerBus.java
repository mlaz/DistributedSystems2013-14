package Servers.bus;

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
public class ServerBus {
	private static final int portNumber = 22163;
	private static final String usage = "Usage: java -jar RMIBus [genRepRegistryName]";
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
		int busInterval = 0;
		int numSeats 	= 0;
		int numEntities = 0;
		try {
			genRep = (IGenRep) genRepRegistry.lookup(RmiUtils.genRepId);
			busInterval = genRep.getBusWaitTime();
			numSeats  	= genRep.getNumBusSeats();
			numEntities = genRep.getNumPassengers() + 2;
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
		MBus bus = new MBus(numSeats, busInterval, genRep, numEntities);
		IBus busInter = null;
        
		try {
			busInter = (IBus) UnicastRemoteObject.exportObject(bus, portNumber);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		
		System.out.println("Bus stub created");
		
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
			registry.bind(RmiUtils.busId, busInter);
		} catch (RemoteException | AlreadyBoundException e) {
			System.err.println("Error binding the Bus to the RMI registry");
			e.printStackTrace();
			System.exit(1);
		}
		
        System.out.println("Bus binded to RMI registry (port " + portNumber + ")");

        try {
			genRep.registerService(RmiUtils.arrivalTerminalId, InetAddress.getLocalHost().getHostName(), portNumber);
		} catch (RemoteException | UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("Ready");
    }
}