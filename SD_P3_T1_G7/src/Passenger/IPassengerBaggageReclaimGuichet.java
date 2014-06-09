package Passenger;

import java.rmi.Remote;
import java.rmi.RemoteException;

import Utils.VectorClock;

/**
 * Interface for communications between a passenger and the Baggage Reclaim Guichet
 * 
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 */
public interface IPassengerBaggageReclaimGuichet extends Remote {

    /**
     * The passengers reports that his bag has been lost
     * @param passengerNumber The ID of this passenger
     * @param vecClock The clock
     * @return The clock
     * @throws RemoteException
     */
    public VectorClock reclaimBags(int passengerNumber, VectorClock vecClock) throws RemoteException;
}
