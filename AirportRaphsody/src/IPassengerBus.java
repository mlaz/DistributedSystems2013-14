/**
 * 
 */

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 *
 */
public interface IPassengerBus {

	/**
	 * @return 
	 * @throws InterruptedException 
	 * 
	 */
	boolean enterTheBus() throws InterruptedException;

	/**
	 * @throws InterruptedException 
	 * 
	 */
	void leaveTheBus() throws InterruptedException;

}
