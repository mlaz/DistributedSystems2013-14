package Porter;
/**
 * 
 */

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 * Interface para interação entre a thread de bagageiro (TPorter) e a zona de recolha de bagagem (MBaggagePickupZone) 
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
