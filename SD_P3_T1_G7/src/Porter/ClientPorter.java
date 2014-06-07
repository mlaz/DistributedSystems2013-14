package Porter;

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
		if (args.length != 1) {
			System.out.println(usage);
			// System.exit(1);
			args = new String[1];
			args[0] = "localhost";
		}
		
		IGenRep genRep = null;
		IPorterArrivalTerminal arrivalTerminal = null;
		IPorterBaggagePickupZone baggageBeltConveyor = null;
		IPorterTempBaggageStorage baggageStorage = null;
		
		try {
			Registry genRepRegistry = LocateRegistry.getRegistry(args[0], RmiUtils.rmiPort);
			genRep = (IGenRep) genRepRegistry.lookup(RmiUtils.genRepId);
			System.out.println( "GenRep RMI registry accessed" );
			
			String arrTermLocation = genRep.getServiceLocation(RmiUtils.arrivalTerminalId);
			Registry arrTermReg	= LocateRegistry.getRegistry(arrTermLocation, RmiUtils.rmiPort);
			arrivalTerminal = (IPorterArrivalTerminal) arrTermReg.lookup(RmiUtils.arrivalTerminalId);
			System.out.println( "Arrival Terminal RMI registry accessed" );
			
			String pickupLocation = genRep.getServiceLocation(RmiUtils.baggagePickupZoneId);
			Registry pickupReg = LocateRegistry.getRegistry(pickupLocation, RmiUtils.rmiPort);
			baggageBeltConveyor = (IPorterBaggagePickupZone) pickupReg.lookup(RmiUtils.baggagePickupZoneId);
			System.out.println( "Baggage Pickup Zone RMI registry accessed" );
			
			String tempStLocation = genRep.getServiceLocation(RmiUtils.tempStorageId);
			Registry tempReg = LocateRegistry.getRegistry(tempStLocation, RmiUtils.rmiPort);
			baggageStorage = (IPorterTempBaggageStorage) tempReg.lookup(RmiUtils.tempStorageId);
			System.out.println( "Temporary Baggage Storage RMI registry accessed" );
			
		} catch ( RemoteException | NotBoundException e2) {
			e2.printStackTrace();
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
