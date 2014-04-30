package Servers.clientsInterfaces;
/**
 * 
 */

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 *
 */
public interface IPassengerArrivalExitTransferZone {

	/**
	 * @param passNumber
	 * @throws InterruptedException
	 */
	void takeABus(int passNumber) throws InterruptedException;
	
	void goHome(int passNumber);
}
