/**
 * @author Miguel Azevedo, Filipe Teixeira
 *
 */
public class MGeneralRepository {
	
	private MArrivalTerminal arrivalTerminal;
	private MArrivalTerminalExit arrivalTerminalExit;
	private MArrivalTerminalTransferZone arrivalTerminalTransferZone;
	private MBaggagePickupZone baggagePickupZone;
	private MBaggageReclaimGuichet baggageReclaimGuichet;
	private MBus bus;
	private MTempBaggageStorage tempBaggageStorage;
	
	public MGeneralRepository() {
		
		
	}
	
	/**
	 * @return the arrivalTerminalExit
	 */
	public MArrivalTerminalExit getArrivalTerminalExit() {
		return arrivalTerminalExit;
	}
	
	/**
	 * @param arrivalTerminalExit the arrivalTerminalExit to set
	 */
	public void setArrivalTerminalExit(MArrivalTerminalExit arrivalTerminalExit) {
		this.arrivalTerminalExit = arrivalTerminalExit;
	}
	
	/**
	 * @return the arrivalTerminalTransferZone
	 */
	public MArrivalTerminalTransferZone getArrivalTerminalTransferZone() {
		return arrivalTerminalTransferZone;
	}
	
	/**
	 * @param arrivalTerminalTransferZone the arrivalTerminalTransferZone to set
	 */
	public void setArrivalTerminalTransferZone(
			MArrivalTerminalTransferZone arrivalTerminalTransferZone) {
		this.arrivalTerminalTransferZone = arrivalTerminalTransferZone;
	}
	
	/**
	 * @return the baggagePickupZone
	 */
	public MBaggagePickupZone getBaggagePickupZone() {
		return baggagePickupZone;
	}
	
	/**
	 * @param baggagePickupZone the baggagePickupZone to set
	 */
	public void setBaggagePickupZone(MBaggagePickupZone baggagePickupZone) {
		this.baggagePickupZone = baggagePickupZone;
	}

	/**
	 * @return the arrivalTerminal
	 */
	public MArrivalTerminal getArrivalTerminal() {
		return arrivalTerminal;
	}

	/**
	 * @param arrivalTerminal the arrivalTerminal to set
	 */
	public void setArrivalTerminal(MArrivalTerminal arrivalTerminal) {
		this.arrivalTerminal = arrivalTerminal;
	}

	/**
	 * @return the baggageReclaimGuichet
	 */
	public MBaggageReclaimGuichet getBaggageReclaimGuichet() {
		return baggageReclaimGuichet;
	}

	/**
	 * @param baggageReclaimGuichet the baggageReclaimGuichet to set
	 */
	public void setBaggageReclaimGuichet(MBaggageReclaimGuichet baggageReclaimGuichet) {
		this.baggageReclaimGuichet = baggageReclaimGuichet;
	}

	/**
	 * @return the bus
	 */
	public MBus getBus() {
		return bus;
	}

	/**
	 * @param bus the bus to set
	 */
	public void setBus(MBus bus) {
		this.bus = bus;
	}

	/**
	 * @return the tempBaggageStorage
	 */
	public MTempBaggageStorage getTempBaggageStorage() {
		return tempBaggageStorage;
	}

	/**
	 * @param tempBaggageStorage the tempBaggageStorage to set
	 */
	public void setTempBaggageStorage(MTempBaggageStorage tempBaggageStorage) {
		this.tempBaggageStorage = tempBaggageStorage;
	}


}
