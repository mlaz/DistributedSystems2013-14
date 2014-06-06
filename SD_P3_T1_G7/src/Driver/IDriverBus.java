package Driver;

import java.rmi.RemoteException;
/**
 * 
 */


import Utils.ClockTuple;
import Utils.VectorClock;

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 * Interface para interação entre a thread de condutor (TDriver) e o autocarro (MBus) 
 */
public interface IDriverBus {

	/**
	 * @param vecClock 
	 * @return 
	 * @throws InterruptedException 
	 * Método chamado pelo condutor para bloqueio à espera que os passageiros entrem no autocarro
	 */
	VectorClock waitingForPassengers(VectorClock vecClock) throws InterruptedException, RemoteException;

	/**
	 * @param vecClock 
	 * @return 
	 * @throws InterruptedException 
	 * Método chamado pelo condutor para bloqueio à espera que os passageiros abandonem o autocarro
	 */
	ClockTuple<Integer> parkAndLetPassOff(VectorClock vecClock) throws InterruptedException, RemoteException;
}
