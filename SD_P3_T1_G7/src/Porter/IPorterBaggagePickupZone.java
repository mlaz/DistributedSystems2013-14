package Porter;

import java.rmi.RemoteException;
/**
 * 
 */


import Utils.ClockTuple;
import Utils.VectorClock;

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 * Interface para interação entre a thread de bagageiro (TPorter) e a zona de recolha de bagagem (MBaggagePickupZone) 
 */
public interface IPorterBaggagePickupZone {

	/**
     * @param passId
	 * @param vecClock 
	 * @return 
	 */
	ClockTuple<Boolean> carryItToAppropriateStore(int passId, VectorClock vecClock) throws RemoteException;

	/**
	 * @param vecClock 
	 * @return 
	 * @throws InterruptedException 
	 * 
	 */
	VectorClock noMoreBagsToCollect(VectorClock vecClock) throws InterruptedException, RemoteException;
}
