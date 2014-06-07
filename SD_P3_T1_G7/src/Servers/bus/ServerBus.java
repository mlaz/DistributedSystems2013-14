package Servers.bus;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import Servers.genRep.IGenRep;
import Utils.RmiUtils;

/**
 * Classe de servidor com replicação para receção de pedidos ao monior por parte das threads(clientes)
 * @author miguel
 */
public class ServerBus {
	private static final int portNumber = 22163;
	private static final String usage = "Usage: java ServerBus [RMIRegName] [RMIRegPort]";
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
		
		/* obter parametros do problema */
		/* get the RMI registry */
		Registry rmiReg = null;
		try {
			rmiReg = RmiUtils.getRMIReg( args[0], Integer.parseInt(args[1]), usage );
		} catch (NumberFormatException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (RemoteException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		System.out.println("RMI registry located");
		
		IGenRep genRep = null;
		try {
			genRep = (IGenRep) rmiReg.lookup(RmiUtils.genRepId);
		} catch (AccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.exit(1);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.exit(1);
		} catch (NotBoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.exit(1);
		}
		
		System.out.println("GenRep accessed");
		
		int busInterval = 0;
		int numSeats 	= 0;
		int numEntities = 0;
		try {
			busInterval = genRep.getBusWaitTime();
			numSeats  	= genRep.getNumBusSeats();
			numEntities = genRep.getNumPassengers() + 2;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		
		/* establecer o serviço */
		MBus bus = new MBus(numSeats, busInterval, genRep, numEntities);
		IBus busInter = null;
        
		try {
			busInter = (IBus) UnicastRemoteObject.exportObject(bus, portNumber);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		
		System.out.println("Bus stub created");
		
		try {
			genRep.bind(RmiUtils.busId, busInter);
		} catch (RemoteException | AlreadyBoundException e) {
			System.err.println("Error binding the Bus to the RMI registry");
			e.printStackTrace();
			System.exit(1);
		}
		
        System.out.println("Bus binded to RMI registry (port " + portNumber + ")");
        System.out.println("Ready");
    }
}