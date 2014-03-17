/**
 * 
 */

/**
 * @author Miguel Azevedo, Filipe Teixeira
 * 
 */
public class TPassenger extends Thread {
	private enum states {
		AT_THE_DISEMBARKING_ZONE, 
		AT_THE_LUGGAGE_COLLECTION_POINT,
		AT_THE_BAGGAGE_RECLAIM_OFFICE, 
		EXITING_THE_ARRIVAL_TERMINAL,
		AT_THE_ARRIVAL_TRANSFER_TERMINAL, 
		TERMINAL_TRANSFER,
		AT_THE_DEPARTURE_TRANSFER_TERMINAL, 
		ENTERING_THE_DEPARTURE_TERMINAL
	}
	
	public void run () {
		states state = states.AT_THE_DISEMBARKING_ZONE;
		while (true) {
			switch (state) {
			case AT_THE_DISEMBARKING_ZONE:
				break;
			case AT_THE_LUGGAGE_COLLECTION_POINT:
				break;
			case AT_THE_BAGGAGE_RECLAIM_OFFICE:
				break;
			case EXITING_THE_ARRIVAL_TERMINAL:
				break;
			case AT_THE_ARRIVAL_TRANSFER_TERMINAL:
				break;
			case TERMINAL_TRANSFER:
				break;
			case AT_THE_DEPARTURE_TRANSFER_TERMINAL:
				break;
			case ENTERING_THE_DEPARTURE_TERMINAL:
				break;
			}	
		}
	}	

}
