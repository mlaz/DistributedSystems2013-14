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
	 * @return 
	 */
	boolean carryItToAppropriateStore(int passId);

	/**
	 * @throws InterruptedException 
	 * 
	 */
	void noMoreBagsToCollect() throws InterruptedException;
}
