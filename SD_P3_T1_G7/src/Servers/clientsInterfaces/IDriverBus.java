package Servers.clientsInterfaces;
/**
 * 
 */

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
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
	 * @throws InterruptedException 
	 * 
	 */
	int parkAndLetPassOff() throws InterruptedException;
}
