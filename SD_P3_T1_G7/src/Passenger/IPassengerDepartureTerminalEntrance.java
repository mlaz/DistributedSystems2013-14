package Passenger;

/**
 * Interface para interação entre a thread de passageiro (TPassenger) e a entrada do terminal de partida (MDepartureTerminalEntrance) 
 * @author miguel
 */
public interface IPassengerDepartureTerminalEntrance {

    /**
     * 
     * @throws InterruptedException
     */
    public void prepareNextLeg() throws InterruptedException;

}
