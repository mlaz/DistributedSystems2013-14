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
	}; 
	
	private IPorterArrivalTerminal arrivalTerminal;
	private IPorterBaggagePickupZone baggageBeltConveyor;
	private MTempBaggageStorage baggageStorage;
	
	
	public TPorter(MGeneralRepository genRep) {

		this.arrivalTerminal = genRep.getArrivalTerminal();
		this.baggageBeltConveyor = genRep.getBaggagePickupZone();
		this.baggageStorage = genRep.getTempBaggageStorage();
	}
	
	public void run () {
		states state = states.WAITING_FOR_A_PLANE_TO_LAND;
		states nextState = state;
		MAirplane currentAirplane = null;
		Bag currentBag = null;
		boolean running = true;
		while (running) {
			switch (state) {
			case WAITING_FOR_A_PLANE_TO_LAND:
				//System.out.println("state = WAITING_FOR_A_PLANE_TO_LAND\n");
				try {
					currentAirplane = arrivalTerminal.takeARest();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				if (currentAirplane != null)
					nextState = states.AT_THE_PLANES_HOLD;
				else 
					running = false;
				break;
				
			case AT_THE_PLANES_HOLD:
				//System.out.println("state = AT_THE_PLANES_HOLD\n");
				if ( (currentBag = currentAirplane.tryToCollectABag ()) == null) {
					currentAirplane = null;
					baggageBeltConveyor.noMoreBagsToCollect();
					nextState = states.WAITING_FOR_A_PLANE_TO_LAND;
					break;
				}
				
				if (currentBag.isInTransit()){
					nextState = states.AT_THE_STOREROOM;
					break;
				}
				
				nextState = states.AT_THE_LUGGAGE_BELT_CONVEYOR;	
				break;
				
			case AT_THE_LUGGAGE_BELT_CONVEYOR:
				//System.out.println("state = AT_THE_LUGGAGE_BELT_CONVEYOR\n");
				baggageBeltConveyor.carryItToAppropriateStore (currentBag);
				currentBag = null;
				nextState = states.AT_THE_PLANES_HOLD;
				break;
				
			case AT_THE_STOREROOM:
				//System.out.println("state = AT_THE_STOREROOM\n");
				baggageStorage.carryItToAppropriateStore (currentBag);
				currentBag = null;
				nextState = states.AT_THE_PLANES_HOLD;
				break;
			}	
			state = nextState;
		}
		System.out.println("Porter Dying!\n");
	}	

}
