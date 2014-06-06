package Driver;

import java.rmi.RemoteException;
/**
 * 
 */


import Utils.ClockTuple;
import Utils.VectorClock;

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 * Interface para interação entre a thread de condutor (TDriver) e a zona de transferencia do terminal de chagada (MArrivalTerminalTransferZone) 
 */
public interface IDriverArrivalTerminalTransferZone {

    /**
     * Método chamado pelo condutor para anunciar a partida do autocarro
     * @param vecClock 
     * @return 
     */
    VectorClock announcingDeparture(VectorClock vecClock) throws RemoteException;

	/**
     * @param lastPassengers
	 * @param vecClock 
	 * @return 
	 * @throws InterruptedException 
	 * Método chamado pelo condutor para anunciar que o autocarro está disponivel
	 */
	ClockTuple<Boolean> announcingBusBoaring(int lastPassengers, VectorClock vecClock)
			throws InterruptedException, RemoteException;
}

