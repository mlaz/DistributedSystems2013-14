/**
 * 
 */

/**
 * @author miguel
 *
 */
public interface IDriverBus {

	/**
	 * @throws InterruptedException 
	 * 
	 */
	void waitingForPassengers() throws InterruptedException;

	/**
	 * @return 
	 * @throws x 
	 * @throws InterruptedException 
	 * 
	 */
	int parkAndLetPassOff() throws InterruptedException;

}
