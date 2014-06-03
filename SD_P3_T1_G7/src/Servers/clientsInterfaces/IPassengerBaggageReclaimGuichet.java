package Servers.clientsInterfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author miguel
 */
public interface IPassengerBaggageReclaimGuichet extends Remote {

    /**
     *
     * @param passengerNumber
     */
    public void reclaimBags(int passengerNumber) throws RemoteException;
}
