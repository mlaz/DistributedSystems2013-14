package Passenger;

import java.rmi.RemoteException;
/**
 * 
 */


import Utils.ClockTuple;
import Utils.VectorClock;

/**
 * Interface for communications between a Passenger and the Baggage Collection Point
 * 
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 */
public interface IPassengerBaggageCollectionPoint {

	/**
	 * The passenger tries to collect a bag.
	 * 
	 * @param passengerNumber The id of this passenger
	 * @param flightNum The number of the flight this passenger is in 
	 * @param vecClock The clock
	 * @return The clock
	 * @throws InterruptedException
	 * @throws RemoteException
	 */
	ClockTuple<Boolean> tryToCollectABag(int passengerNumber, int flightNum, VectorClock vecClock)
			throws InterruptedException, RemoteException;
}
