package Driver;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

import Utils.RmiUtils;

/**
 * Classe ClientDriver: classe para lançar a thread TDriver
 * @author miguel
 */
public class ClientDriver {

	final static String usage = "Usage: java ClientDriver [genRepName] [genRepPort]";
			
    /**
     *
     * @param args genRepName genRepPort
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
		} catch (NumberFormatException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (RemoteException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		IDriverArrivalTerminalTransferZone arrivalTerminalTransferZone = null;
		IDriverBus bus = null;
		IDriverGenRep genRep = null;
		try {
			arrivalTerminalTransferZone = (IDriverArrivalTerminalTransferZone) reg.lookup(RmiUtils.arrivalTerminalTransferZoneId);
			bus = (IDriverBus) reg.lookup(RmiUtils.busId);
			genRep = (IDriverGenRep) reg.lookup(RmiUtils.genRepId);
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

		TDriver driver = new TDriver(genRep, arrivalTerminalTransferZone, bus);
		driver.start();
		
		try {
			driver.join();
			genRep.setDriverAsDead();
		} catch (InterruptedException | RemoteException e) {
			e.printStackTrace();
		}
	}
}
