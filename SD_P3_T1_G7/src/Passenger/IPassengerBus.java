package Passenger;

import java.rmi.RemoteException;

import Utils.ClockTuple;
import Utils.VectorClock;

/**
 * 
 */

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 * Interface para interação entre a thread de passageiro (TPassenger) e o autocarro (MBus) 
 */
public interface IPassengerBus {

	/**
     * @param passNum
	 * @param vecClock 
	 * @return 
	 * @throws InterruptedException 
	 * 
	 */
	ClockTuple<Boolean> enterTheBus(int passNum, VectorClock vecClock) throws InterruptedException, RemoteException;
	
	/**
     * @param passNum
	 * @param vecClock 
	 * @return 
	 * @throws InterruptedException 
	 * 
	 */
	VectorClock leaveTheBus(int passNum, VectorClock vecClock) throws InterruptedException, RemoteException;
	

	
}
