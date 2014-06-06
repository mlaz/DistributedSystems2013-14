package Passenger;

import java.rmi.Remote;
import java.rmi.RemoteException;

import Utils.VectorClock;

/**
 * Interface para interação entre a thread de condutor (TDriver) e guichet de reclamação de bagagens (MBaggageReclaimGuichet) 
 * @author miguel
 */
public interface IPassengerBaggageReclaimGuichet extends Remote {

    /**
     *
     * @param passengerNumber
     * @param vecClock 
     * @return 
     */
    public VectorClock reclaimBags(int passengerNumber, VectorClock vecClock) throws RemoteException;
}
