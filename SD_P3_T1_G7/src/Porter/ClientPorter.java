package Porter;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import Servers.genRep.IGenRep;
import Utils.RmiUtils;

/**
 * Classe ClientDriver: classe para lançar a thread TPorter
 * @author miguel
 */
public class ClientPorter {

	final static String usage = "Usage: java -jar RMIPorter [genRepRegistryName]";
    /**
     *
     * @param args [genRepName] [genRepPort]
     */
    public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println(usage);
			// System.exit(1);
			args = new String[2];
			args[0] = "localhost";
			args[1] = "22168";
		}
		
		Registry genRepRegistry = null;
		try {
			genRepRegistry = LocateRegistry.getRegistry(args[0], RmiUtils.rmiPort);
		} catch( RemoteException e ) {
			System.err.println( "Error accessing the RMI registry: " + e.getMessage() );
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println( "GenRep RMI registry accessed" );
		
		Registry reg = null;
		try {
			reg = RmiUtils.getRMIReg(args[0], Integer.parseInt(args[1]), usage);
		} catch (NumberFormatException | RemoteException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		IPorterArrivalTerminal arrivalTerminal = null;
		IPorterBaggagePickupZone baggageBeltConveyor = null;
		IPorterTempBaggageStorage baggageStorage = null;
		IGenRep genRep = null;
		try {
			arrivalTerminal = (IPorterArrivalTerminal) reg.lookup(RmiUtils.arrivalTerminalId);
			baggageBeltConveyor = (IPorterBaggagePickupZone) reg.lookup(RmiUtils.baggagePickupZoneId);
			baggageStorage = (IPorterTempBaggageStorage) reg.lookup(RmiUtils.tempStorageId);
			genRep = (IGenRep) reg.lookup(RmiUtils.genRepId);
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
		
		int numIdentities = 0;
		try {
			numIdentities = genRep.getNumPassengers() + 2;
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int clockIndex = 1;
		TPorter porter = new TPorter(numIdentities, clockIndex, (IPorterGenRep)genRep, arrivalTerminal, baggageBeltConveyor, baggageStorage);
		porter.start();
		
		try {
			porter.join();
			genRep.setPorterAsDead();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
