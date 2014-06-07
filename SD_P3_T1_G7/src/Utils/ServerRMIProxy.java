package Utils;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServerRMIProxy {
	
	private static final String usage = "null";
	private static final int	portNumber = 22169;
	
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println(usage);
			// System.exit(1);
			args = new String[2];
			args[0] = "localhost";
			args[1] = "22168";
		}
		
		RMIProxy proxy = new RMIProxy(args[0], Integer.parseInt(args[1]));
		IRMIProxy proxyInter = null;
		
		try {
			proxyInter = (IRMIProxy) UnicastRemoteObject.exportObject(proxy, portNumber);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Registry reg = null;
		
		try {
			reg = LocateRegistry.getRegistry(args[0], Integer.parseInt(args[1]));
		} catch (NumberFormatException | RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			reg.bind(RmiUtils.rmiProxyId, proxyInter);
		} catch ( RemoteException | AlreadyBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
}
