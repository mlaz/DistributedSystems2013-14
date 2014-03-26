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
	boolean enterTheBus(int passNum) throws InterruptedException;
	
	/**
	 * @throws InterruptedException 
	 * 
	 */
	void leaveTheBus(int passNum) throws InterruptedException;
	

	
}
