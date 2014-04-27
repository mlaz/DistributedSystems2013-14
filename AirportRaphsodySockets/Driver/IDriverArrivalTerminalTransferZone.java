package Driver;
/**
 * 
 */

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 *
 */
public interface IDriverArrivalTerminalTransferZone {

	void announcingDeparture();

	/**
	 * @return 
	 * @throws InterruptedException 
	 * 
	 */
	boolean announcingBusBoaring(int lastPassengers)
			throws InterruptedException;
}

