package Utils;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiUtils {
	static final int waitTime 	 = 500;
	static final int maxAttempts = 10;
	
	public static final String genRepId 						= "genRep";
	public static final String tempStorageId 					= "tempStorage";
	public static final String arrivalTerminalId 				= "arrivalTerminal";
	public static final String arrivalTerminalTransferZoneId 	= "arrivalTerminalTransferZone";
	public static final String busId 							= "bus";
	public static final String baggagePickupZoneId 				= "baggagePickupZone";
	public static final String baggageReclaimGuichetId 			= "baggageReclaimGuichet";
	public static final String departureTerminalEntraceZoneId 	= "departureTerminalEntraceId";
	public static final String rmiProxyId 						= "rmiProxy";
	
	public static Registry getRMIReg( String hostname, int port, String usage ) throws RemoteException {
    	Registry rmiReg 	  = null;
    	boolean registered 	  = true;
    	int remainingAttempts = maxAttempts;
    	
		do {
			try {
				rmiReg = LocateRegistry.getRegistry(hostname, port);	
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
