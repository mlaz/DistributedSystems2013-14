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
	 * @return
	 * @throws InterruptedException 
	 */
	boolean tryToCollectABag(int passengerNumber, int flightNum)
			throws InterruptedException;
}
