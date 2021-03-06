/**
 * 
 */

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 * 
 */
public class TPorter extends Thread {
	public enum states {
		WAITING_FOR_A_PLANE_TO_LAND, 
		AT_THE_PLANES_HOLD,
		AT_THE_LUGGAGE_BELT_CONVEYOR,
		AT_THE_STOREROOM
	}; 
	
	private IPorterArrivalTerminal arrivalTerminal;
	private IPorterBaggagePickupZone baggageBeltConveyor;
	private MTempBaggageStorage baggageStorage;
	private IPorterGenRep genRep;
	
	
	public TPorter(IPorterGenRep genRep) {

		this.arrivalTerminal = genRep.getArrivalTerminal();
		this.baggageBeltConveyor = genRep.getBaggagePickupZone();
		this.baggageStorage = genRep.getTempBaggageStorage();
		this.genRep = genRep;
		genRep.registerPorter();
	}
	
	public void run () {		

		states state = states.WAITING_FOR_A_PLANE_TO_LAND;
		states nextState = state;
		Bag currentBag = null;
		boolean running = true;
		while (running) {
			switch (state) {
			case WAITING_FOR_A_PLANE_TO_LAND:
				System.out.println("state = WAITING_FOR_A_PLANE_TO_LAND\n");
				try {
					if (arrivalTerminal.takeARest())
						nextState = states.AT_THE_PLANES_HOLD;
					else
						running = false;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
				
			case AT_THE_PLANES_HOLD:
				System.out.println("state = AT_THE_PLANES_HOLD\n");
				if ( (currentBag = arrivalTerminal.tryToCollectABag ()) == null) {
					nextState = states.AT_THE_LUGGAGE_BELT_CONVEYOR;
					break;
				}
				
				nextState = (currentBag.isInTransit()) ? states.AT_THE_STOREROOM : 
					states.AT_THE_LUGGAGE_BELT_CONVEYOR;
				genRep.removeLuggageAtPlane();
				break;
				
			case AT_THE_LUGGAGE_BELT_CONVEYOR:
				System.out.println("state = AT_THE_LUGGAGE_BELT_CONVEYOR\n");
				if (currentBag == null) {
					try {
						baggageBeltConveyor.noMoreBagsToCollect();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					nextState = states.WAITING_FOR_A_PLANE_TO_LAND;
					break;
				}
				if (baggageBeltConveyor.carryItToAppropriateStore (currentBag.getPassNumber()))
					genRep.incLuggageAtCB();
				currentBag = null;
				nextState = states.AT_THE_PLANES_HOLD;
				break;
				
			case AT_THE_STOREROOM:
				System.out.println("state = AT_THE_STOREROOM\n");
				baggageStorage.carryItToAppropriateStore (currentBag);
				genRep.incLuggageAtSR();
				currentBag = null;
				nextState = states.AT_THE_PLANES_HOLD;
				break;
			}	
			state = nextState;
			genRep.updatePorterState(state);
		}
		System.out.println("Porter Dying!\n");
	}	
}
