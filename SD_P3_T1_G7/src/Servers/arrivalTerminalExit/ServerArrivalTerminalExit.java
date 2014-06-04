package Servers.arrivalTerminalExit;

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
public class ServerArrivalTerminalExit {

	private static final int portNumber = 22162;
	private static final String usage 	= "Usage: java ServerArrivalTerminalExit [RMIRegName] [RmiRegPort]";

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
			args[1] = "22168";
		}

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
		
		/* obter parametros do problema */
		int numFlights = 0;
		int numPassengers = 0;
		int numSeats = 0;
		try {
			numFlights 	  = genRep.getNumFlights();
			numPassengers = genRep.getNumPassengers();
			numSeats 	  = genRep.getNumBusSeats();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/* establecer o serviço */
		MArrivalTerminalExit arrivalTerminalExit = new MArrivalTerminalExit(numFlights, numPassengers, numSeats, genRep);
		IArrivalTerminalExit arrivalTerminalExitInter = null;
		try {
			arrivalTerminalExitInter = (IArrivalTerminalExit) UnicastRemoteObject.exportObject(arrivalTerminalExit, portNumber);
		} catch (RemoteException e) {
			System.err.println("Error creating the ArrivalTerminalExit stub");
			e.printStackTrace();
			System.exit(1);
		}
		
		System.out.println("Arrival Terminal Exit stub created");
		
		try {
			rmiReg.bind(RmiUtils.arrivalTerminalTransferZoneId, arrivalTerminalExitInter);
		} catch (RemoteException | AlreadyBoundException e) {
			System.err.println("Error binding the ArrivalTermminalExit to the RMI registry");
			e.printStackTrace();
			System.exit(1);
		}
		
        System.out.println("Arrival Terminal Exit binded to RMI registry (port " + portNumber + ")");
	}
}