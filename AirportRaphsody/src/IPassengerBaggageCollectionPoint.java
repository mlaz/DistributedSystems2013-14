/**
 * 
 */

/**
 * @author miguel
 *
 */
public interface IPassengerBaggageCollectionPoint {

	/**
	 * @param passengerNumber 
	 * @return
	 * @throws InterruptedException 
	 */
	boolean tryToCollectABag(int passengerNumber) throws InterruptedException;

}
