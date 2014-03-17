/**
 * 
 */

/**
 * @author Miguel Azevedo, Filipe Teixeira
 * 
 */
public class TPorter extends Thread {
	private enum states {
		WAITING_FOR_A_PLANE_TO_LAND, 
		AT_THE_PLANES_HOLD,
		AT_THE_LUGGAGE_BELT_CONVEYOR, 
		AT_THE_STOREROOM
	}
	
	public void run () {
		states state = states.WAITING_FOR_A_PLANE_TO_LAND;
		while (true) {
			switch (state) {
			case WAITING_FOR_A_PLANE_TO_LAND:
				break;
			case AT_THE_PLANES_HOLD:
				break;
			case AT_THE_LUGGAGE_BELT_CONVEYOR:
				break;
			case AT_THE_STOREROOM:
				break;
			}	
		}
	}	

}
