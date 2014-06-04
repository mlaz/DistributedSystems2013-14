package Servers.baggagePickupZone;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import Utils.RmiUtils;

/**
 * Classe de servidor com replicação para receção de pedidos ao monior por parte das threads(clientes)
 * @author miguel
 */
public class ServerBaggagePickupZone {
	private static int portNumber = 22164;
	private static String usage = "Usage: java ServerArrivalTerminal [thisMachineName] [genRepName] [genRepPort]";

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
		MBaggagePickupZone baggagePickupZone = new MBaggagePickupZone();
		IBaggagePickupZone baggagePickupInter = null;
		
		try {
			baggagePickupInter = (IBaggagePickupZone) UnicastRemoteObject.exportObject(baggagePickupZone, portNumber);
		} catch (RemoteException e) {
			System.err.println("Error creating the TempStorage stub");
			e.printStackTrace();
			System.exit(1);
		}
		
		System.out.println("TempStorage stub created");
		
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
			rmiReg.bind(RmiUtils.baggagePickupZoneId, baggagePickupInter);
		} catch (RemoteException | AlreadyBoundException e) {
			System.err.println("Error binding the BaggagePickupZone to the RMI registry");
			e.printStackTrace();
			System.exit(1);
		}
		
		System.out.println("Baggage Pickup Zone service is listening on port " + portNumber + "...");
		
		
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