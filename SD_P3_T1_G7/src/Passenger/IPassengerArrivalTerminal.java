package Passenger;

import java.rmi.RemoteException;

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 * Interface para interação entre a thread de condutor (TDriver) e o terminal de chagada (MArrivalTerminal) 
 */
public interface IPassengerArrivalTerminal {

	/**
	 * @param passengerId
	 * @throws InterruptedException 
	 */
	void whatSouldIDo(int passengerId) throws InterruptedException, RemoteException;

}
