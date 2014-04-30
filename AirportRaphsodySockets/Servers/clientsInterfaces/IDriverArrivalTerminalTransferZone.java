package Servers.clientsInterfaces;
/**
 * 
 */

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 *
 */
public interface IDriverArrivalTerminalTransferZone {

    /**
     *
     */
    void announcingDeparture();

	/**
     * @param lastPassengers
	 * @return 
	 * @throws InterruptedException 
	 * 
	 */
	boolean announcingBusBoaring(int lastPassengers)
			throws InterruptedException;
}

