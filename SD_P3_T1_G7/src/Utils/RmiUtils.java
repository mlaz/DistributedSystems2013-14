package Utils;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Defines some useful constants and methods for the RMI communications.
 * 
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 */
public class RmiUtils {
	/**
	 * Time to be waited between successive retries to access the rmi registry. 
	 */
	private static final int waitTime 	 = 500;
	/**
	 * The maximum number of retries to access the rmi registry.
	 */
	private static final int maxAttempts = 10;
	
	/**
	 * The id of the General Repository service.
	 */
	public static final String genRepId 						= "genRep";
	/**
	 * The id of the Temporary Baggage Storage service.
	 */
	public static final String tempStorageId 					= "tempStorage";
	/**
	 * The id of the Arrival Terminal service.
	 */
	public static final String arrivalTerminalId 				= "arrivalTerminal";
	/**
	 * The id of the Arrival Terminal Transfer Zone (or Exit) service.
	 */
	public static final String arrivalTerminalTransferZoneId 	= "arrivalTerminalTransferZone";
	/**
	 * The id of the Bus service.
	 */
	public static final String busId 							= "bus";
	/**
	 * The id of the Baggage Pickup Zone service.
	 */
	public static final String baggagePickupZoneId 				= "baggagePickupZone";
	/**
	 * The id of the Baggage Reclaim Guichet service.
	 */
	public static final String baggageReclaimGuichetId 			= "baggageReclaimGuichet";
	/**
	 * The id of the Departure Terminal Entrance service.
	 */
	public static final String departureTerminalEntraceZoneId 	= "departureTerminalEntraceId";
	/**
	 * The port used for the RMI registry in all terminals.
	 */
	public static final int rmiPort = 22168;
	
	/**
	 * Tries to access the requested rmi registry and, in case of failure, retries [maxAttempts] times.
	 * @param hostname The name of the terminal that hosts the RMI registry
	 * @param usage The usage string of the service calling this method.
	 * @return The requested RMI registry and a NULL in case of failure.
	 * @throws RemoteException
	 */
	public static Registry getRMIReg( String hostname, String usage ) throws RemoteException {
    	Registry rmiReg 	  = null;
    	boolean registered 	  = true;
    	int remainingAttempts = maxAttempts;
    	
		do {
			try {
				rmiReg = LocateRegistry.getRegistry(hostname, rmiPort);	
			} catch (NumberFormatException e) {
				System.err.println("The third argument isn't a valid port");
				System.out.println(usage);
				e.printStackTrace();
				System.exit(1);
			} catch (RemoteException e) {
				System.err.println("Can't locate the RMI registry. Waiting ("+waitTime+"ms)");
				remainingAttempts--;
				
				if( remainingAttempts == 0)
					throw e;
				
				registered = false;
				try {
					Thread.sleep(waitTime);
				} catch( InterruptedException e1) {
					// TODO
				}
			}
		} while( !registered && remainingAttempts > 0);
		
		return rmiReg;
    }    
}
