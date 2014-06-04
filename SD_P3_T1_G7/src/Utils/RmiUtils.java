package Utils;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiUtils {
	static final int waitTime = 500;
	static int remainingAttempts = 10;
	
	public static final String genRepId = "genRep";
	public static String tempStorageId = "tempStorage";
	public static String baggageBeltConveyorId = "baggageBeltConveyor";
	public static String arrivalTerminalId = "arrivalTerminal";
	public static String arrivalTerminalTransferZoneId = "arrivalTerminalTransferZone";
	public static String busId = "bus";
	public static String baggageReclaimOfficeId = "baggageReclaimOffice";
	public static String baggagePickupZoneId = "baggagePickupZone";
	
	public static Registry getRMIReg( String hostname, int port, String usage ) throws RemoteException {
    	Registry rmiReg = null;
    	boolean registered = true;
    	
    	
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
