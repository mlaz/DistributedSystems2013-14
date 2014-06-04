package Servers.arrivalTerminalExit;

import java.rmi.RemoteException;

public interface IArrivalTerminalExitGenRep {

	void updateDriverQueue(int[] intArray) throws RemoteException;

}
