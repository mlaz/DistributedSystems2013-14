package Driver;

import java.rmi.RemoteException;
/**
 * 
 */


import Utils.ClockTuple;
import Utils.VectorClock;


/**
 * Interface for the Driver to communicate with the Arrival Terminal Transfer Zone
 * 
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 */
public interface IDriverArrivalTerminalTransferZone {

    /**
     * The driver announces that the bus is leaving
     * @param vecClock The clock
     * @return The clock 
     */
    VectorClock announcingDeparture(VectorClock vecClock) throws RemoteException;

	/**
	 * The driver announces that the bus is available to be boarded.
	 * @param lastPassengers The number of passengers carried in the last trip
	 * @param vecClock The clock
	 * @return A ClockTuple with the Clock and a boolean that's true when there will be more work to be done and false if the driver can die.
	 * @throws InterruptedException
	 * @throws RemoteException
	 */
	ClockTuple<Boolean> announcingBusBoaring(int lastPassengers, VectorClock vecClock)
			throws InterruptedException, RemoteException;
}

