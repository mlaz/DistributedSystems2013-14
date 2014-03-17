/**
 * 
 */

/**
 * @author Miguel Azevedo, Filipe Teixeira
 * 
 */
public class TBusDriver extends Thread {
	private enum states {
		PARKING_AT_THE_ARRIVAL_TERMINAL, 
		DRIVING_FORWARD,
		PARKING_AT_THE_DEPARTURE_TERMINAL,
		DRIVING_BACKWARD
	}
	
	public void run () {
		states state = states.PARKING_AT_THE_ARRIVAL_TERMINAL;
		while (true) {
			switch (state) {
			case PARKING_AT_THE_ARRIVAL_TERMINAL:
				break;
			case DRIVING_FORWARD:
				break;
			case PARKING_AT_THE_DEPARTURE_TERMINAL:
				break;
			case DRIVING_BACKWARD:
				break;
			}	
		}
	}

}
