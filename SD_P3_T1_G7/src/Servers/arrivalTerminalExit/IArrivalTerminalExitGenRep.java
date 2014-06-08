package Servers.arrivalTerminalExit;

import java.rmi.RemoteException;

import Utils.VectorClock;

/**
 * This interface contains the methods that the Bus has to interact with the General Repository
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 */
public interface IArrivalTerminalExitGenRep {

	/**
	 * Reports to the General Repository who is currently on the queue waiting for the bus
	 * @param intArray The passengers waiting
	 * @param clk The clock
	 * @throws RemoteException
	 */
	void updateDriverQueue(int[] intArray, VectorClock clk) throws RemoteException;

}
