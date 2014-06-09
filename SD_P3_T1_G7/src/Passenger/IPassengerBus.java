package Passenger;

import java.rmi.RemoteException;

import Utils.ClockTuple;
import Utils.VectorClock;

/**
 * Interface for communications between a Passenger and a Bus
 *  
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 */
public interface IPassengerBus {

	/**
	 * The passenger tries enters the bus
	 * 
     * @param passNum The id of this passenger
	 * @param vecClock The clock
	 * @return A ClockTuple with a clock and a boolean that's true if the passenger entered the bus.
	 * @throws InterruptedException 
	 * @throws RemoteException 
	 */
	ClockTuple<Boolean> enterTheBus(int passNum, VectorClock vecClock) throws InterruptedException, RemoteException;
	
	/**
	 * The passenger leaves the bus
	 * 
     * @param passNum The id of this passenger
	 * @param vecClock The clock
	 * @return The clock
	 * @throws InterruptedException 
	 * @throws RemoteException
	 */
	VectorClock leaveTheBus(int passNum, VectorClock vecClock) throws InterruptedException, RemoteException;
	

	
}
