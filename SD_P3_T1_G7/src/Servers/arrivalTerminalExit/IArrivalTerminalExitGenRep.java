package Servers.arrivalTerminalExit;

import java.rmi.RemoteException;

import Utils.VectorClock;

public interface IArrivalTerminalExitGenRep {

	void updateDriverQueue(int[] intArray, VectorClock clk) throws RemoteException;

}
