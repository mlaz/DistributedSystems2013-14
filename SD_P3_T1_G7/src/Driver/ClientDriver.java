package Driver;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import Servers.genRep.IGenRep;
import Utils.RmiUtils;

/**
 * Classe ClientDriver: classe para lançar a thread TDriver
 * @author miguel
 */
public class ClientDriver {

	final static String usage = "Usage: java -jar RMIDriver [genRepRegistryName]";
			
    /**
     *
     * @param args genRepName genRepPort
     */
    public static void main(String[] args) {
		
    	if (args.length != 1) {
			System.out.println(usage);
			// System.exit(1);
			args = new String[2];
			args[0] = "localhost";
		}
		
		IGenRep genRep = null;
		IDriverArrivalTerminalTransferZone arrivalTerminalTransferZone = null;
		IDriverBus bus = null;
		try {
			Registry genRepRegistry = LocateRegistry.getRegistry(args[0], RmiUtils.rmiPort);
			genRep = (IGenRep) genRepRegistry.lookup(RmiUtils.genRepId);
			System.out.println( "GenRep RMI registry accessed" );
			
			String transferLocation = genRep.getServiceLocation(RmiUtils.arrivalTerminalTransferZoneId);
			Registry transferReg	= LocateRegistry.getRegistry(transferLocation, RmiUtils.rmiPort);
			arrivalTerminalTransferZone = (IDriverArrivalTerminalTransferZone) transferReg.lookup(RmiUtils.arrivalTerminalTransferZoneId);
			
			String busLocation = genRep.getServiceLocation(RmiUtils.busId);
			Registry busReg	= LocateRegistry.getRegistry(busLocation, RmiUtils.rmiPort);
			bus = (IDriverBus) busReg.lookup(RmiUtils.busId);
		} catch ( RemoteException | NotBoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			System.exit(1);
		}
		
		int numEntities = 0;
		try {
			numEntities = genRep.getNumPassengers() + 2;
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.exit(1);
		}
		int clockIndex    = 0;
		TDriver driver = new TDriver(numEntities, clockIndex, (IDriverGenRep)genRep, arrivalTerminalTransferZone, bus);
		driver.start();
		
		try {
			driver.join();
			genRep.setDriverAsDead();
		} catch (InterruptedException | RemoteException e) {
			e.printStackTrace();
		}
	}
}
