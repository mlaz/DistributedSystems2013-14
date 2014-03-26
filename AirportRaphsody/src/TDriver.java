/**
 * 
 */

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 * 
 */
public class TDriver extends Thread {
	public enum states {
		PARKING_AT_THE_ARRIVAL_TERMINAL, 
		DRIVING_FORWARD,
		PARKING_AT_THE_DEPARTURE_TERMINAL,
		DRIVING_BACKWARD
	}
	
	private IDriverArrivalTerminalTransferZone arrivalTerminalTransferZone;
	private IDriverBus bus;
	private int lastPassengers = 0;
	private IDriverGenRep genRep;
	
	/**
	 * @param genRep
	 */
	public TDriver(IDriverGenRep genRep) {
		arrivalTerminalTransferZone = genRep.getArrivalTerminalExit();
		bus = genRep.getBus();
		this.genRep = genRep;
		genRep.registerDriver();
	}

	public void run () {
	
		states state = states.PARKING_AT_THE_ARRIVAL_TERMINAL;
		states nextState = state;
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
				nextState = states.DRIVING_FORWARD;
				break;
				
			case DRIVING_FORWARD:
				//System.out.println("DRIVER: DRIVING_FORWARD");

				nextState = states.PARKING_AT_THE_DEPARTURE_TERMINAL;
				break;
				
			case PARKING_AT_THE_DEPARTURE_TERMINAL:
				//System.out.println("DRIVER: PARKING_AT_THE_DEPARTURE_TERMINAL");

				try {
					lastPassengers = bus.parkAndLetPassOff();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				nextState = states.DRIVING_BACKWARD;
				break;
				
			case DRIVING_BACKWARD:
				//System.out.println("DRIVER: DRIVING_BACKWARD");

				nextState = states.PARKING_AT_THE_ARRIVAL_TERMINAL;
				break;
			}	
			state = nextState;
			genRep.updateDriverState(state);
		}
		System.out.println("Driver Dying!\n+++++++++++++++++++++++++++++++++++++++++++");
	}

}
