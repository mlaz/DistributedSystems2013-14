package Passenger;

import java.rmi.Remote;

/**
 * Interface para interação entre a thread de condutor (TDriver) e guichet de reclamação de bagagens (MBaggageReclaimGuichet) 
 * @author miguel
 */
public interface IPassengerBaggageReclaimGuichet extends Remote {

    /**
     *
     * @param passengerNumber
     */
    public void reclaimBags(int passengerNumber);
}
