package Passenger;

import java.rmi.RemoteException;
/**
 * 
 */

import Utils.VectorClock;

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 * Interface para interação entre a thread de passageiro (TPassenger) e a zona de transferencia do terminal de chagada (MArrivalTerminalExitTransferZone) 
 */
public interface IPassengerArrivalExitTransferZone {

	/**
	 * @param passNumber The ID of the passenger
	 * @param vecClock The clock
	 * @return The clock
	 * @throws InterruptedException
	 */
	VectorClock takeABus(int passNumber, VectorClock vecClock) throws InterruptedException, RemoteException;

    /**
     *
     * @param passNumber
     * @param vecClock 
     * @return 
     */
    VectorClock goHome(int passNumber, VectorClock vecClock) throws RemoteException;
}
