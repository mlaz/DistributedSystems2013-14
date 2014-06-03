package Servers.tempStorage;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import Servers.ServerInfo;

/**
 * Classe de servidor com replicação para receção de pedidos ao monior por parte das threads(clientes)
 * @author miguel
 */
public class ServerTempBaggaStorage {
	private static int portNumber = 22167;
	private static String hostName;
	private static ServerInfo genRepInfo;
	private static String usage = "Usage: java ServerTempBaggageStorage [thisMachineName] [RMIRegName] [RMIRegPort]";
	private static int waitTime = 500;	//time, in ms, between successive attempts to obtain a remote object
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
			args[2] = "22159";		//TODO this is wrong
		}
		/* obter parametros do problema */
		hostName = args[0];
		
		/* get the RMI registry */
		Registry rmiReg = getRMIReg( args[1], Integer.parseInt( args[2] ) );
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
    
    private static Registry getRMIReg( String hostname, int port ) {
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
    
    private static ITempBaggageStorageGenRep getGenRep( Registry rmiReg ) {
    	ITempBaggageStorageGenRep genRep = null;
    	boolean registered = true;
    	int remainingAttempts = 5;
    	
		do {
			try {
				genRep = (ITempBaggageStorageGenRep) rmiReg.lookup("genRep");
			} catch (AccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				System.out.println("GenRep is not registered in RMI. Waiting ("+waitTime+"ms)");
				remainingAttempts--;
				registered = false;
				
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
				}
			}
		} while( !registered && remainingAttempts > 0);
 
		return genRep;
    }
}