package Passenger;
/**
 * 
 */

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 * Interface para interação entre a thread de passageiro (TPassenger) e a zona de transferencia do terminal de chagada (MArrivalTerminalExitTransferZone) 
 */
public interface IPassengerArrivalExitTransferZone {

	/**
	 * @param passNumber
	 * @throws InterruptedException
	 */
	void takeABus(int passNumber) throws InterruptedException;

    /**
     *
     * @param passNumber
     */
    void goHome(int passNumber);
}
