package Driver;

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 * Classe TDriver: classe de implementação da thread de condutor de autocarro
 */
public class TDriver extends Thread {
	
	private IDriverArrivalTerminalTransferZone arrivalTerminalTransferZone;
	private IDriverBus bus;
	private int lastPassengers = 0;
	private IDriverGenRep genRep;
	
	/**
	 * @param genRep
	 * Construtor da classe TDriver
	 */
	public TDriver(IDriverGenRep genRep) {
		arrivalTerminalTransferZone = genRep.getArrivalTerminalExit();
		bus = genRep.getBus();
		this.genRep = genRep;
		genRep.registerDriver();
	}

    /**
     * Implementação da máquina de estados da thread
     */
    public void run () {
		
		EDriverStates state = EDriverStates.PARKING_AT_THE_ARRIVAL_TERMINAL;
		EDriverStates nextState = state;
		boolean running = true;
		while (running) {
			switch (state) {
			case PARKING_AT_THE_ARRIVAL_TERMINAL:
				//System.out.println("DRIVER: PARKING_AT_THE_ARRIVAL_TERMINAL");
				try {
					
					if (!(running = arrivalTerminalTransferZone.announcingBusBoaring(lastPassengers)))
						break;
					lastPassengers = 0;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//System.out.println("DRIVER: PARKING_AT_THE_ARRIVAL_TERMINAL 2nd stage");
				
				try {
					bus.waitingForPassengers();
					arrivalTerminalTransferZone.announcingDeparture();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				nextState = EDriverStates.DRIVING_FORWARD;
				break;
				
			case DRIVING_FORWARD:
				//System.out.println("DRIVER: DRIVING_FORWARD");

				nextState = EDriverStates.PARKING_AT_THE_DEPARTURE_TERMINAL;
				break;
				
			case PARKING_AT_THE_DEPARTURE_TERMINAL:
				//System.out.println("DRIVER: PARKING_AT_THE_DEPARTURE_TERMINAL");

				try {
					//lastPassengers = bus.parkAndLetPassOff();
					lastPassengers = bus.parkAndLetPassOff();
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				nextState = EDriverStates.DRIVING_BACKWARD;
				break;
				
			case DRIVING_BACKWARD:
				//System.out.println("DRIVER: DRIVING_BACKWARD");

				nextState = EDriverStates.PARKING_AT_THE_ARRIVAL_TERMINAL;
				break;
			}	
			state = nextState;
			genRep.updateDriverState(state);
		}
		System.out.println("Driver Dying!\n+++++++++++++++++++++++++++++++++++++++++++");
	}

}
