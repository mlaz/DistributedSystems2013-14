package Passenger;

import java.rmi.RemoteException;
/**
 * 
 */

import Utils.VectorClock;

/**
 * Interface for communications between a Passenger and the Arrival Terminal Exit
 * 
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 */
public interface IPassengerArrivalExitTransferZone {

	/**
	 * The passenger goes to the bus queue
	 * @param passNumber The ID of the passenger
	 * @param vecClock The clock
	 * @return The clock
	 * @throws InterruptedException
	 */
	VectorClock takeABus(int passNumber, VectorClock vecClock) throws InterruptedException, RemoteException;

    /**
     * The passenger goes home
     * 
     * @param passNumber The id of this passenger
     * @param vecClock The clock
     * @return The clock
     * @throws RemoteException
     */
    VectorClock goHome(int passNumber, VectorClock vecClock) throws RemoteException;
}
