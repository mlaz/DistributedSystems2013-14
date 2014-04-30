package Porter;
/**
 * 
 */

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 *
 */
public interface IPorterBaggagePickupZone {

	/**
     * @param passId
	 * @return 
	 */
	boolean carryItToAppropriateStore(int passId);

	/**
	 * @throws InterruptedException 
	 * 
	 */
	void noMoreBagsToCollect() throws InterruptedException;
}
