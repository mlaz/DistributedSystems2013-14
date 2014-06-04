package Porter;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

import Utils.RmiUtils;

/**
 * Classe ClientDriver: classe para lançar a thread TPorter
 * @author miguel
 */
public class ClientPorter {

	final static String usage = "Usage: java ClientPorter [genRepName] [genRepPort]";
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
		IPorterGenRep genRep = null;
		try {
			arrivalTerminal = (IPorterArrivalTerminal) reg.lookup(RmiUtils.arrivalTerminalId);
			baggageBeltConveyor = (IPorterBaggagePickupZone) reg.lookup(RmiUtils.baggageBeltConveyorId);
			baggageStorage = (IPorterTempBaggageStorage) reg.lookup(RmiUtils.tempStorageId);
			genRep = (IPorterGenRep) reg.lookup(RmiUtils.genRepId);
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
		
		TPorter porter = new TPorter(genRep, arrivalTerminal, baggageBeltConveyor, baggageStorage);
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
