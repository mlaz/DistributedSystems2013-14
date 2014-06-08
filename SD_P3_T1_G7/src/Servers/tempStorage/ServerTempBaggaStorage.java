package Servers.tempStorage;

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
 * This classe establishes a new service, the Temporary Baggage Storage.
 * The communications use RMI.
 * 
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 */
public class ServerTempBaggaStorage {
	private static final int portNumber = 22167;
	private static final String usage 	= "Usage: java -jar RMITemporaryBaggageStorage [genRepRegistryName]";
    /**
     * Starts the server.
     * @param args Only one argument is allowed. The name of the server hosting the General Repository
     */
    public static void main(String[] args) {
		
		if (args.length != 1) {
			System.out.println( usage );
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
		int numEntities = 0;
		try {
			genRep = (IGenRep) genRepRegistry.lookup(RmiUtils.genRepId);
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
		MTempBaggageStorage tempStorage = new MTempBaggageStorage(numEntities);
		ITempStorage tempStorageInter   = null;
		
		try {
			tempStorageInter = (ITempStorage) UnicastRemoteObject.exportObject(tempStorage, portNumber);
		} catch (RemoteException e) {
			System.err.println("Error creating the TempStorage stub");
			e.printStackTrace();
			System.exit(1);
		}
		
		System.out.println("TempStorage stub created");
		
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
			registry.bind(RmiUtils.tempStorageId, tempStorageInter);
		} catch (RemoteException | AlreadyBoundException e) {
			System.err.println("Error binding the TempStorage to the RMI registry");
			e.printStackTrace();
			System.exit(1);
		}
		
		System.out.println("Temporary Baggage Storage binded to RMI registry (port "+portNumber+")");

		try {
			genRep.registerService(RmiUtils.tempStorageId, InetAddress.getLocalHost().getHostName());
		} catch (RemoteException | UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Ready");
	}
}