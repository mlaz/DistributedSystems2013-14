package Servers.baggageReclaimGuichet;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import Servers.genRep.IGenRep;
import Utils.RmiUtils;

/**
 * This class establishes a new service, the Baggage Reclaim Guichet.
 * The communications use RMI.
 * 
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 */
public class ServerBaggageReclaimGuichet {
	/**
	 * The port this service will use 
	 */
	private static final int portNumber = 22165;
	/**
	 * How to launch this service
	 */
	private static final String usage = "Usage: java -jar RMIBaggageReclaimGuichet [genRepRegistryName]";


    /**
     * Starts the server.
     * @param args Only one argument is allowed. The name of the server hosting the General Repository
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
		
		
		int numEntities = 0;
		IGenRep genRep = null;
		try {
			genRep = (IGenRep) genRepRegistry.lookup(RmiUtils.genRepId);
			numEntities = genRep.getNumPassengers() + 2;
		} catch ( RemoteException | NotBoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		/* establecer o serviço */
		MBaggageReclaimGuichet baggageReclaim = new MBaggageReclaimGuichet(numEntities);
		IBaggageReclaimGuichet baggageReclaimInter   = null;
		
		try {
			baggageReclaimInter = (IBaggageReclaimGuichet) UnicastRemoteObject.exportObject(baggageReclaim, portNumber);
		} catch (RemoteException e) {
			System.err.println("Error creating the BaggageReclaimGuichet stub");
			e.printStackTrace();
			System.exit(1);
		}
		
		System.out.println("BaggageReclameGuichet stub created");
		
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
			registry.bind(RmiUtils.baggageReclaimGuichetId, baggageReclaimInter);
		} catch (RemoteException | AlreadyBoundException e) {
			System.err.println("Error binding the BaggageReclaimGuichet to the RMI registry");
			e.printStackTrace();
			System.exit(1);
		}
		
		System.out.println("Baggage Reclaim Guichet binded to RMI registry (port " + portNumber + ")");
		 try {
				genRep.registerService(RmiUtils.baggageReclaimGuichetId, InetAddress.getLocalHost().getHostName());
			} catch (RemoteException | UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		System.out.println("Ready");			
	}
}