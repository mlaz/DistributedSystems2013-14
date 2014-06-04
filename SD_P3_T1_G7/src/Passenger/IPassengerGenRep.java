package Passenger;


/**
 * @author Miguel Azevedo, Filipe Teixeira
 * Interface para interação entre a thread de passageiro (TPassenger) e a entrada do terminal de partida (MDepartureTerminalEntrance) 
 */
public interface IPassengerGenRep {

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
