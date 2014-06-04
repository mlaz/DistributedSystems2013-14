package Servers.baggageReclaimGuichet;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import Utils.RmiUtils;

/**
 *
 * @author miguel
 */
public class ServerBaggageReclaimGuichet {
	private static int portNumber = 22165;
	private static String usage = "Usage: java ServerBaggageReclaimGuichet [thisMachineName] [genRepName] [genRepPort]";


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
			args[2] = "22168";
		}
		/* establecer o serviço */
		MBaggageReclaimGuichet baggageReclaim = new MBaggageReclaimGuichet();
		IBaggageReclaimGuichet baggageReclaimInter   = null;
		
		try {
			baggageReclaimInter = (IBaggageReclaimGuichet) UnicastRemoteObject.exportObject(baggageReclaim, portNumber);
		} catch (RemoteException e) {
			System.err.println("Error creating the BaggageReclaimGuichet stub");
			e.printStackTrace();
			System.exit(1);
		}
		
		System.out.println("BaggageReclameGuichet stub created");
		
		/* get the RMI registry */
		Registry rmiReg = null;
		try {
			rmiReg = RmiUtils.getRMIReg( args[1], Integer.parseInt(args[2]), usage );
		} catch (NumberFormatException e1) {
			System.err.println("The second argument isn't a valid port number");
			e1.printStackTrace();
			System.exit(1);
		} catch (RemoteException e1) {
			System.err.println("The RMI registry is unavailable");
			e1.printStackTrace();
			System.exit(1);
		}
		
		System.out.println("RMI registry located!");
		
		
		try {
			rmiReg.bind(RmiUtils.baggageReclaimGuichetId, baggageReclaimInter);
		} catch (RemoteException | AlreadyBoundException e) {
			System.err.println("Error binding the BaggageReclaimGuichet to the RMI registry");
			e.printStackTrace();
			System.exit(1);
		}
		
		System.out.println("Baggage Reclaim Guichet service is listening on port " + portNumber + "...");
		
		
		System.out.println("REGISTRY:");
		try {
			for( String s : rmiReg.list() ) {
				System.out.println("   "+s);
			}
		} catch( RemoteException e ) {
			e.printStackTrace();
		}
	
	}
}