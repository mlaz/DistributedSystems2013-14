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
	private static int portNumber = 22163;
	private static final String usage = "Usage: java ServerBus [thisMachineName] [genRepName] [genRepPort]";
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
		/* obter parametros do problema */
		/* get the RMI registry */
		Registry rmiReg = null;
		try {
			rmiReg = RmiUtils.getRMIReg( args[1], Integer.parseInt(args[2]), usage );
		} catch (NumberFormatException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (RemoteException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		System.out.println("RMI registry located!");
		
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
		
		int numPassengers = 0;
		int numSeats 	  = 0;
		try {
			numPassengers = genRep.getNumPassengers();
			numSeats  	  = genRep.getNumBusSeats();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		
		/* establecer o serviço */
		MBus bus = new MBus(numSeats, numPassengers, genRep);
		IBus busInter = null;
        
		try {
			busInter = (IBus) UnicastRemoteObject.exportObject(bus, portNumber);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		
		try {
			rmiReg.bind(RmiUtils.busId, busInter);
		} catch (RemoteException | AlreadyBoundException e) {
			System.err.println("Error binding the Bus to the RMI registry");
			e.printStackTrace();
			System.exit(1);
		}
		
        System.out.println("Bus service is listening on port " + portNumber + "...");
    }
}