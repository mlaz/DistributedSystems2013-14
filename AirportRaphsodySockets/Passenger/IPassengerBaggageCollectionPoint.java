package Passenger;
/**
 * 
 */

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 *
 */
public interface IPassengerBaggageCollectionPoint {

	/**
	 * @param passengerNumber 
     * @param flightNum 
	 * @return
	 * @throws InterruptedException 
	 */
	boolean tryToCollectABag(int passengerNumber, int flightNum)
			throws InterruptedException;
}
