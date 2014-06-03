package Porter;

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 * Classe TPassanger: classe de implementação da thread de passageiro
 */
public class TPorter extends Thread {
	
	private IPorterArrivalTerminal arrivalTerminal;
	private IPorterBaggagePickupZone baggageBeltConveyor;
	private IPorterTempBaggageStorage baggageStorage;
	private IPorterGenRep genRep;

    /**
     * Construtor da Classe
     * @param genRep
     */
    public TPorter(IPorterGenRep genRep) {

		this.arrivalTerminal 	 = genRep.getArrivalTerminal();
		this.baggageBeltConveyor = genRep.getBaggagePickupZone();
		this.baggageStorage 	 = genRep.getTempBaggageStorage();
		this.genRep 		 	 = genRep;
		genRep.registerPorter();
	}

    /**
     * Implementação da máquina de estados da thread
     */
    public void run () {		

		EPorterStates state = EPorterStates.WAITING_FOR_A_PLANE_TO_LAND;
		EPorterStates nextState = state;
		Bag currentBag = null;
		boolean running = true;
		while (running) {
			switch (state) {
			case WAITING_FOR_A_PLANE_TO_LAND:
				System.out.println("state = WAITING_FOR_A_PLANE_TO_LAND\n");
				try {
					if (arrivalTerminal.takeARest())
						nextState = EPorterStates.AT_THE_PLANES_HOLD;
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
					nextState = EPorterStates.AT_THE_LUGGAGE_BELT_CONVEYOR;
					break;
				}
				
				nextState = (currentBag.isInTransit()) ? EPorterStates.AT_THE_STOREROOM : 
					EPorterStates.AT_THE_LUGGAGE_BELT_CONVEYOR;
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
					nextState = EPorterStates.WAITING_FOR_A_PLANE_TO_LAND;
					break;
				}
				if (baggageBeltConveyor.carryItToAppropriateStore (currentBag.getPassNumber()))
					genRep.incLuggageAtCB();
				currentBag = null;
				nextState = EPorterStates.AT_THE_PLANES_HOLD;
				break;
				
			case AT_THE_STOREROOM:
				System.out.println("state = AT_THE_STOREROOM\n");
				baggageStorage.carryItToAppropriateStore (currentBag);
				genRep.incLuggageAtSR();
				currentBag = null;
				nextState = EPorterStates.AT_THE_PLANES_HOLD;
				break;
			}	
			state = nextState;
			genRep.updatePorterState(state);
		}
		System.out.println("Porter Dying!\n");
	}	
}
