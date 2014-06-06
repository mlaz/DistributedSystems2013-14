package Servers.departureTerminalEntrance;

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
public class ServerDepartureTerminalEntrance {
	private static final int portNumber = 22166;
	private static final String usage = "Usage: java ServerDepartureTerminalEntrance [RMIRegName] [RMIRegPort]";

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
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NotBoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println("GenRep accessed");
		
		/* obter parametros do problema */			
		int numPassengers = 0;
		try {
			numPassengers = genRep.getNumPassengers();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int numEntities = 0;
		try {
			numEntities = genRep.getNumPassengers()+2;
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.exit(1);
		}
		
		/* establecer o serviço */
		MDepartureTerminalEntrance DepartureTerminalEntrance = new MDepartureTerminalEntrance(numPassengers, numEntities);
		IDepartureTerminalEntrance DepartureTerminalEntranceInter = null;
		try {
			DepartureTerminalEntranceInter = (IDepartureTerminalEntrance) UnicastRemoteObject.exportObject(DepartureTerminalEntrance, portNumber);
		} catch (RemoteException e) {
			System.err.println("Error creating the DepartureTerminalEntrace stub");
			e.printStackTrace();
			System.exit(1);
		}
		
		System.out.println( "Departure Terminal Entrance stub created" );
		
		try {
			rmiReg.bind(RmiUtils.departureTerminalEntraceZoneId, DepartureTerminalEntranceInter);
		} catch (RemoteException | AlreadyBoundException e) {
			System.err.println("Error binding the DepartureTerminalEntrace to the RMI registry");
			e.printStackTrace();
			System.exit(1);
		}
		
        System.out.println("Departure Terminal Entrance binded to RMI registry (port "+portNumber+")");
        System.out.println("Ready");

	}
	
}