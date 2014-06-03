package Servers.clientsInterfaces;
/**
 * 
 */

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 *
 */
public interface IPassengerBus {

	/**
     * @param passNum
	 * @return 
	 * @throws InterruptedException 
	 * 
	 */
	boolean enterTheBus(int passNum) throws InterruptedException;
	
	/**
     * @param passNum
	 * @throws InterruptedException 
	 * 
	 */
	void leaveTheBus(int passNum) throws InterruptedException;
	

	
}
