package Servers.baggageReclaimGuichet;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import Servers.genRep.IGenRep;
import Utils.RmiUtils;

/**
 *
 * @author miguel
 */
public class ServerBaggageReclaimGuichet {
	private static final int portNumber = 22165;
	private static final String usage = "Usage: java ServerBaggageReclaimGuichet [RMIRegName] [RMIRegPort]";


    /**
     *
     * @param args
     */
    public static void main(String[] args) {
		
		if (args.length != 2) {
			System.out.println(usage);
			// System.exit(1);
			args = new String[2];
			args[0] = "localhost";
			args[1] = "22168";
		}

		/* get the RMI registry */
		Registry rmiReg = null;
		try {
			rmiReg = RmiUtils.getRMIReg( args[0], Integer.parseInt(args[1]), usage );
		} catch (NumberFormatException e1) {
			System.err.println("The second argument isn't a valid port number");
			e1.printStackTrace();
			System.exit(1);
		} catch (RemoteException e1) {
			System.err.println("The RMI registry is unavailable");
			e1.printStackTrace();
			System.exit(1);
		}
		
		System.out.println("RMI registry located");
		
		
		int numEntities = 0;
		try {
			IGenRep genRep = (IGenRep) rmiReg.lookup(RmiUtils.genRepId);
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
		
		try {
			rmiReg.bind(RmiUtils.baggageReclaimGuichetId, baggageReclaimInter);
		} catch (RemoteException | AlreadyBoundException e) {
			System.err.println("Error binding the BaggageReclaimGuichet to the RMI registry");
			e.printStackTrace();
			System.exit(1);
		}
		
		System.out.println("Baggage Reclaim Guichet binded to RMI registry (port " + portNumber + ")");
		System.out.println("Ready");
		
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