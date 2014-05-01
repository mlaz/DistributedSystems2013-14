package Driver;
/**
 * 
 */

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 * Interface para interação entre a thread de condutor (TDriver) e o autocarro (MBus) 
 */
public interface IDriverBus {

	/**
	 * @throws InterruptedException 
	 * Método chamado pelo condutor para bloqueio à espera que os passageiros entrem no autocarro
	 */
	void waitingForPassengers() throws InterruptedException;

	/**
	 * @return 
	 * @throws InterruptedException 
	 * Método chamado pelo condutor para bloqueio à espera que os passageiros abandonem o autocarro
	 */
	int parkAndLetPassOff() throws InterruptedException;
}
