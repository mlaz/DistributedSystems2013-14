package Servers.tempStorage;


import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import Servers.genRep.IGenRep;
import Utils.IRMIProxy;
import Utils.RmiUtils;

/**
 * Classe de servidor com replicação para receção de pedidos ao monior por parte das threads(clientes)
 * @author miguel
 */
public class ServerTempBaggaStorage {
	private static final int portNumber = 22167;
	private static final String usage 	= "Usage: java ServerTempBaggageStorage [RMIRegName] [RMIRegPort]";
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
		
		if (args.length != 2) {
			System.out.println( usage );
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
			System.exit(1);
		}
		
		/* establecer o serviço */
		MTempBaggageStorage tempStorage = new MTempBaggageStorage(numEntities);
		
		ITempStorage tempStorageInter   = null;
		
		try {
			tempStorageInter = (ITempStorage) UnicastRemoteObject.exportObject(tempStorage, portNumber);
		} catch (RemoteException e) {
			System.err.println("Error creating the TempStorage stub");
			e.printStackTrace();
			System.exit(1);
		}
		
		System.out.println("TempStorage stub created");
		
		IRMIProxy proxy = null;
		try {
			proxy = (IRMIProxy)rmiReg.lookup(RmiUtils.rmiProxyId);
		} catch ( RemoteException | NotBoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			proxy.bind(RmiUtils.tempStorageId, tempStorageInter);
		} catch (RemoteException | AlreadyBoundException e) {
			System.err.println("Error binding the TempStorage to the RMI registry");
			e.printStackTrace();
			System.exit(1);
		}
		
		System.out.println("Temporary Baggage Storage binded to RMI registry (port "+portNumber+")");
		System.out.println("Ready");
		
		//TODO delete this
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