/**
 * 
 */

/**
 * @author miguel
 *
 */
public interface IPorterBaggagePickupZone {

	/**
	 * @param currentBag
	 */
	void carryItToAppropriateStore(Bag currentBag);

	/**
	 * 
	 */
	void noMoreBagsToCollect();

}
