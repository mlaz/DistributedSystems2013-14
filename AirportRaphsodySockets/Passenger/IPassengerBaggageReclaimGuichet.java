package Passenger;

/**
 * Interface para interação entre a thread de condutor (TDriver) e guichet de reclamação de bagagens (MBaggageReclaimGuichet) 
 * @author miguel
 */
public interface IPassengerBaggageReclaimGuichet {

    /**
     *
     * @param passengerNumber
     */
    public void reclaimBags(int passengerNumber);
}
