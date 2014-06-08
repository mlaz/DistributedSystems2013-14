package Driver;

import java.rmi.RemoteException;
/**
 * 
 */


import Utils.ClockTuple;
import Utils.VectorClock;


/**
 * Interface for communication between the Driver and the Bus
 * 
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 */
public interface IDriverBus {

	/**
	 * Blocks the driver waiting for passengers to enter the bus
	 * @param vecClock The clock
	 * @return The clock
	 * @throws InterruptedException
	 * @throws RemoteException
	 */
	VectorClock waitingForPassengers(VectorClock vecClock) throws InterruptedException, RemoteException;

	/**
	 * Blocks the driver waiting for passengers to leave the bus
	 * @param vecClock
	 * @return
	 * @throws InterruptedException
	 * @throws RemoteException
	 */
	ClockTuple<Integer> parkAndLetPassOff(VectorClock vecClock) throws InterruptedException, RemoteException;
}
