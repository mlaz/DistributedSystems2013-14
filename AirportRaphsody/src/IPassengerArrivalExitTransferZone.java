/**
 * 
 */

/**
 * @author miguel
 *
 */
public interface IPassengerArrivalExitTransferZone {

	/**
	 * @param passNumber
	 * @throws InterruptedException
	 */
	void takeABus(int passNumber) throws InterruptedException;
	void goHome();
}
