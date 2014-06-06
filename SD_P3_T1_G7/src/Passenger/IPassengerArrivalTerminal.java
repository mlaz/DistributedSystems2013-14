package Passenger;

import java.rmi.RemoteException;

import Utils.VectorClock;

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 * Interface para interação entre a thread de condutor (TDriver) e o terminal de chagada (MArrivalTerminal) 
 */
public interface IPassengerArrivalTerminal {

	/**
	 * @param passengerId
	 * @param vecClock 
	 * @return 
	 * @throws InterruptedException 
	 */
	VectorClock whatSouldIDo(int passengerId, VectorClock vecClock) throws InterruptedException, RemoteException;

}
