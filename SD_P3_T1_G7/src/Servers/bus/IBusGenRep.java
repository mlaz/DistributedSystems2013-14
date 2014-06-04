package Servers.bus;

import java.rmi.RemoteException;

public interface IBusGenRep {
	void updateDriverSeats(int[] seats) throws RemoteException;
}
