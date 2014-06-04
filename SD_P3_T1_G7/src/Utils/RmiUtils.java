package Utils;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiUtils {
	static final int waitTime = 500;
	public static final String genRepId = "genRep";
	public static String tempStorageId = "tempStorage";
	public static String baggageBeltConveyorId = "baggageBeltConveyor";
	public static String arrivalTerminalId = "arrivalTerminal";
	
	public static Registry getRMIReg( String hostname, int port, String usage ) {
    	Registry rmiReg = null;
    	boolean registered = true;
    	int remainingAttempts = 5;
    	
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