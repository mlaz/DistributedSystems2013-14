/**
 * 
 */

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 *
 */
public interface IPorterBaggagePickupZone {

	/**
	 * @param currentBag
	 */
	void carryItToAppropriateStore(int passId);

	/**
	 * @throws InterruptedException 
	 * 
	 */
	void noMoreBagsToCollect() throws InterruptedException;
}
