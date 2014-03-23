/**
 * 
 */

/**
 * @author Miguel Azevedo, Filipe Teixeira
 * 
 */
public class TDriver extends Thread {
	private enum states {
		PARKING_AT_THE_ARRIVAL_TERMINAL, 
		DRIVING_FORWARD,
		PARKING_AT_THE_DEPARTURE_TERMINAL,
		DRIVING_BACKWARD
	}
	
	private IDriverArrivalTerminalTransferZone arrivalTerminalTransferZone;
	private IDriverBus bus;
	
	/**
	 * @param genRep
	 */
	public TDriver(MGeneralRepository genRep) {
		arrivalTerminalTransferZone = genRep.getArrivalTerminalTransferZone();
		bus = genRep.getBus();
	}

	public void run () {
		states state = states.PARKING_AT_THE_ARRIVAL_TERMINAL;
		states nextState = state;
		while (true) {
			switch (state) {
			case PARKING_AT_THE_ARRIVAL_TERMINAL:
				System.out.println("DRIVER: PARKING_AT_THE_ARRIVAL_TERMINAL");
				
				try {
					arrivalTerminalTransferZone.announcingBusBoaring();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				System.out.println("DRIVER: PARKING_AT_THE_ARRIVAL_TERMINAL 2nd stage");
				
				try {
					bus.waitingForPassengers();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				nextState = states.DRIVING_FORWARD;
				break;
				
			case DRIVING_FORWARD:
				System.out.println("DRIVER: DRIVING_FORWARD");

				nextState = states.PARKING_AT_THE_DEPARTURE_TERMINAL;
				break;
				
			case PARKING_AT_THE_DEPARTURE_TERMINAL:
				System.out.println("DRIVER: PARKING_AT_THE_DEPARTURE_TERMINAL");

				try {
					bus.parkAndLetPassOff();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				nextState = states.DRIVING_BACKWARD;
				break;
				
			case DRIVING_BACKWARD:
				System.out.println("DRIVER: DRIVING_BACKWARD");

				nextState = states.PARKING_AT_THE_ARRIVAL_TERMINAL;
				break;
			}	
			state = nextState;
		}
	}

}
