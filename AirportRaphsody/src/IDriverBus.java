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
	 * @throws x 
	 * @throws InterruptedException 
	 * 
	 */
	void parkAndLetPassOff() throws InterruptedException;

}
