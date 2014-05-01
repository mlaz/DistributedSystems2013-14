package Driver;
/**
 * 
 */

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 * Interface para interação entre a thread de condutor (TDriver) e a zona de transferencia do terminal de chagada (MArrivalTerminalTransferZone) 
 */
public interface IDriverArrivalTerminalTransferZone {

    /**
     * Método chamado pelo condutor para anunciar a partida do autocarro
     */
    void announcingDeparture();

	/**
     * @param lastPassengers
	 * @return 
	 * @throws InterruptedException 
	 * Método chamado pelo condutor para anunciar que o autocarro está disponivel
	 */
	boolean announcingBusBoaring(int lastPassengers)
			throws InterruptedException;
}

