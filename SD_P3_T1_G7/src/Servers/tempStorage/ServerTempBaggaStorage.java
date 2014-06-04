package Servers.tempStorage;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import Utils.RmiUtils;

/**
 * Classe de servidor com replicação para receção de pedidos ao monior por parte das threads(clientes)
 * @author miguel
 */
public class ServerTempBaggaStorage {
	private static int portNumber = 22167;
	private static String hostName;
	private static String usage = "Usage: java ServerTempBaggageStorage [thisMachineName] [RMIRegName] [RMIRegPort]";
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
		
		if (args.length != 3) {
			System.out.println( usage );
			// System.exit(1);
			args = new String[3];
			args[0] = "localhost";
			args[1] = "localhost";
			args[2] = "22168";		//TODO this is wrong
		}
		/* obter parametros do problema */
		hostName = args[0];
		
		/* get the RMI registry */
		Registry rmiReg = RmiUtils.getRMIReg( args[1], Integer.parseInt(args[2]), usage );
		System.out.println("RMI registry located!");
		
		/* establecer o serviço */
		MTempBaggageStorage tempStorage = new MTempBaggageStorage();
		try {
			rmiReg.bind("tempStorage", tempStorage);
		} catch (RemoteException | AlreadyBoundException e) {
			e.printStackTrace();
		}
		
		System.out.println("Temporary Baggage Storage service is listening on port " + portNumber + "...");
		
		
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