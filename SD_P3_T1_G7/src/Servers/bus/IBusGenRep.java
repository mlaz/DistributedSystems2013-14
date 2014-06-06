package Servers.bus;

import java.rmi.RemoteException;

import Utils.VectorClock;

public interface IBusGenRep {
	void updateDriverSeats(int[] seats, VectorClock clk) throws RemoteException;
}
