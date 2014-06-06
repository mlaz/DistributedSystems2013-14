package Passenger;

import java.rmi.RemoteException;
/**
 * 
 */


import Utils.ClockTuple;
import Utils.VectorClock;

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 * Interface para interação entre a thread de condutor (TDriver) e a zona de recolha de bagagens (MBaggageCollectionZone) 
 */
public interface IPassengerBaggageCollectionPoint {

	/**
	 * @param passengerNumber 
     * @param flightNum 
	 * @param vecClock 
	 * @return
	 * @throws InterruptedException 
	 */
	ClockTuple<Boolean> tryToCollectABag(int passengerNumber, int flightNum, VectorClock vecClock)
			throws InterruptedException, RemoteException;
}
