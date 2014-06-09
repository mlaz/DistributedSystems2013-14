package Porter;

import java.rmi.RemoteException;
/**
 * 
 */


import Utils.ClockTuple;
import Utils.VectorClock;

/**
 * Interface for communications between the Porter and the Baggage Pickup Zone
 * 
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 */
public interface IPorterBaggagePickupZone {

	/** 
	 * The porter carries the Bag to the correct destination, either the Temporary Storage or the Conveyor Belt
     * @param passId The ID of the passenger who owns the bag
	 * @param vecClock The clock
	 * @return The clock
	 */
	ClockTuple<Boolean> carryItToAppropriateStore(int passId, VectorClock vecClock) throws RemoteException;

	/**
	 * The porter signals the passengers waiting that there are no more bags.
	 * @param vecClock The clock
	 * @return The clock
	 * @throws InterruptedException 
	 * 
	 */
	VectorClock noMoreBagsToCollect(VectorClock vecClock) throws InterruptedException, RemoteException;
}
