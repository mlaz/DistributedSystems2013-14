package Utils;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRMIProxy extends Remote {
	public void bind(String id, Remote rem) throws RemoteException, AlreadyBoundException;
}
