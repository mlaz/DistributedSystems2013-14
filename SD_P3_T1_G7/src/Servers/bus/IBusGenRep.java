package Servers.bus;

import java.rmi.RemoteException;

import Utils.VectorClock;

/**
 * This interface contains the methods that the Bus has to interact with the General Repository
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 */
public interface IBusGenRep {
	/**
	 * Reports to the General Repository who is currently occupying the bus seats
	 * @param seats The occupation of the seats
	 * @param clk The clock
	 * @throws RemoteException
	 */
	void updateDriverSeats(int[] seats, VectorClock clk) throws RemoteException;
}
