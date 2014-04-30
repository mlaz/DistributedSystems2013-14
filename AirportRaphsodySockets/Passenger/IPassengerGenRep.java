package Passenger;


/**
 * @author Miguel Azevedo, Filipe Teixeira
 *
 */
public interface IPassengerGenRep {

    /**
     *
     * @return
     */
    IPassengerArrivalTerminal getArrivalTerminal();

    /**
     *
     * @return
     */
    IPassengerBaggageCollectionPoint getBaggagePickupZone();

    /**
     *
     * @return
     */
    IPassengerBaggageReclaimGuichet getBaggageReclaimGuichet();

    /**
     *
     * @return
     */
    IPassengerArrivalExitTransferZone getArrivalTerminalExit();

    /**
     *
     * @return
     */
    IPassengerDepartureTerminalEntrance getDepartureTerminalEntrace();

    /**
     *
     * @return
     */
    IPassengerBus getBus();

    /**
     *
     * @param passengerNumber
     * @param flightNumber
     * @param inTransit
     * @param remainingBags
     */
    void registerPassenger(int passengerNumber, int flightNumber,
			boolean inTransit, int remainingBags);

    /**
     *
     * @param passengerNumber
     */
    void gotLuggage(int passengerNumber);

    /**
     *
     * @param passengerNumber
     * @param state
     */
    void setPassengerStat(int passengerNumber, EPassengerStates state);

}
