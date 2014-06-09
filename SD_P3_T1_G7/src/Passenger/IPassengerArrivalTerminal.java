package Passenger;

import java.rmi.RemoteException;

import Utils.VectorClock;

/**
 * Interface for communications between a Passenger and the Arrival Terminal
 * 
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 */
public interface IPassengerArrivalTerminal {

	/**
	 * The passenger arrives at the airport.
	 * @param passengerId The id of the passenger
	 * @param vecClock The clock
	 * @return The clock
	 * @throws InterruptedException
	 * @throws RemoteException
	 */
	VectorClock whatSouldIDo(int passengerId, VectorClock vecClock) throws InterruptedException, RemoteException;

}
