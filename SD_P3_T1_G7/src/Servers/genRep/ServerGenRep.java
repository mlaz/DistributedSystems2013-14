package Servers.genRep;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import Utils.RmiUtils;


/**
 * Classe de servidor com replicação para receção de pedidos ao monior por parte das threads(clientes)
 * @author miguel
 */
public class ServerGenRep {

	private static final int portNumber = 22160;
	private static final int rmiPort	= 22168;
	private static String logFile;

    /**
     *
     * @param args
     */
    public static void main(String[] args) {

		for( String arg : args ) {
			System.out.println(arg);
		}
		
		if (args.length != 6) { 
			System.out.println("Usage: java ServerGenRep [logFile] [numFlights] [numPassengers] [numBusSeats] [maxBags] [busTimer_in_ms]");
			// System.exit(1);
			args = new String[6];
			args[0] = "log2.log";
			args[1] = "6";
			args[2] = "10";
			args[3] = "3";
			args[4] = "2";
			args[5] = "2000";
		}
		
		/* obter parametros do problema */
		logFile	 			= args[0];
		int numFlights 		= Integer.parseInt(args[1]);
		int numPassengers 	= Integer.parseInt(args[2]);
		int numSeats 		= Integer.parseInt(args[3]);
		int maxBags 		= Integer.parseInt(args[4]);
		int busTimer 		= Integer.parseInt(args[5]);		
		
		Registry registry = null;
		
		try {
			registry = LocateRegistry.getRegistry(rmiPort);
		} catch( RemoteException e ) {
			System.err.println( "Error accessing the RMI registry: " + e.getMessage() );
			e.printStackTrace();
			System.exit(1);
		}
		
		System.out.println( "RMI registry accessed" );
		
		/* establecer o servico */
		MGeneralRepository genRep = new MGeneralRepository(numPassengers, numSeats, busTimer, numFlights, maxBags, logFile);
		IGenRep genRepInter = null;
		
		try {
			genRepInter = (IGenRep) UnicastRemoteObject.exportObject( genRep, portNumber );
		} catch (RemoteException e) {
			System.err.println("Error creating the GenRep stub");
			e.printStackTrace();
			System.exit(1);
		}		
		
		System.out.println( "GenRep stub created" );
		
		try {
			registry.bind( RmiUtils.genRepId, genRepInter );
		} catch (AccessException e) {
			System.err.println( "Registry access error" );
			e.printStackTrace();
			System.exit(1);
		} catch (RemoteException e) {
			System.err.println( "Error registering the GenRep" );
			e.printStackTrace();
			System.exit(1);
		} catch (AlreadyBoundException e) {
			System.err.println( "GenRep is already registered" );
			e.printStackTrace();
			System.exit(1);
		}
		
		System.out.println( "GenRep binded to RMI registry (port "+portNumber+")" );
		System.out.println( "Ready" );
	}
}