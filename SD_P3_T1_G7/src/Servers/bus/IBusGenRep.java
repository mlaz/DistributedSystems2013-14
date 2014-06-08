package Servers.bus;

import java.rmi.RemoteException;

import Utils.VectorClock;

/**
 * This interface contains that the Bus has to interact with the General Repository
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 */
public interface IBusGenRep {
	void updateDriverSeats(int[] seats, VectorClock clk) throws RemoteException;
}
