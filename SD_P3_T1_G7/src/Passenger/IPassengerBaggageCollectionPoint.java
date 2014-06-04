package Passenger;

import java.rmi.RemoteException;
/**
 * 
 */

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 * Interface para interação entre a thread de condutor (TDriver) e a zona de recolha de bagagens (MBaggageCollectionZone) 
 */
public interface IPassengerBaggageCollectionPoint {

	/**
	 * @param passengerNumber 
     * @param flightNum 
	 * @return
	 * @throws InterruptedException 
	 */
	boolean tryToCollectABag(int passengerNumber, int flightNum)
			throws InterruptedException, RemoteException;
}
