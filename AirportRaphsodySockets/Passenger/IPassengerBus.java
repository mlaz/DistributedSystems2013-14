package Passenger;
/**
 * 
 */

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 * Interface para interação entre a thread de passageiro (TPassenger) e o autocarro (MBus) 
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
