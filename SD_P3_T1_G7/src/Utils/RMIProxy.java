package Utils;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIProxy implements IRMIProxy {

	private String 	rmiHostname;
	private int		rmiPort;
	
	public RMIProxy(String rmiHost, int rmiPort) {
		this.rmiHostname = rmiHost;
		this.rmiPort = rmiPort;
	}

	@Override
	public void bind(String id, Remote rem) throws RemoteException,
			AlreadyBoundException {
		Registry reg = null;
		
		reg = LocateRegistry.getRegistry(rmiHostname, rmiPort);
		reg.bind(id, rem);
		
	}
	
}
