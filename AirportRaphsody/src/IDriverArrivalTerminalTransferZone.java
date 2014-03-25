/**
 * 
 */

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 *
 */
public interface IDriverArrivalTerminalTransferZone {

	/**
	 * @return 
	 * @throws InterruptedException 
	 * 
	 */
	
	void announcingDeparture();
	boolean announcingBusBoaring(int lastPassengers)
			throws InterruptedException;
}

