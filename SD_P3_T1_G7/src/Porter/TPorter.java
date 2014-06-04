package Porter;

import java.rmi.RemoteException;

import Utils.Bag;

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
    public TPorter(IPorterGenRep genRep, 
    			IPorterArrivalTerminal arrivalTerminal, 
    			IPorterBaggagePickupZone baggageBeltConveyor, 
    			IPorterTempBaggageStorage baggageStorage) {

		this.arrivalTerminal 	 = arrivalTerminal;
		this.baggageBeltConveyor = baggageBeltConveyor;
		this.baggageStorage 	 = baggageStorage;
		this.genRep 		 	 = genRep;
		try {
			genRep.registerPorter();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
					try {
						if (arrivalTerminal.takeARest())
							nextState = EPorterStates.AT_THE_PLANES_HOLD;
						else
							running = false;
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
				
			case AT_THE_PLANES_HOLD:
				System.out.println("state = AT_THE_PLANES_HOLD\n");
				try {
					if ( (currentBag = arrivalTerminal.tryToCollectABag ()) == null) {
						nextState = EPorterStates.AT_THE_LUGGAGE_BELT_CONVEYOR;
						break;
					}
				} catch (RemoteException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				nextState = (currentBag.isInTransit()) ? EPorterStates.AT_THE_STOREROOM : 
					EPorterStates.AT_THE_LUGGAGE_BELT_CONVEYOR;
				try {
					genRep.removeLuggageAtPlane();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
				
			case AT_THE_LUGGAGE_BELT_CONVEYOR:
				System.out.println("state = AT_THE_LUGGAGE_BELT_CONVEYOR\n");
				if (currentBag == null) {
					try {
						try {
							baggageBeltConveyor.noMoreBagsToCollect();
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					nextState = EPorterStates.WAITING_FOR_A_PLANE_TO_LAND;
					break;
				}
				try {
					if (baggageBeltConveyor.carryItToAppropriateStore (currentBag.getPassNumber()))
						try {
							genRep.incLuggageAtCB();
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				currentBag = null;
				nextState = EPorterStates.AT_THE_PLANES_HOLD;
				break;
				
			case AT_THE_STOREROOM:
				System.out.println("state = AT_THE_STOREROOM\n");
				try {
					baggageStorage.carryItToAppropriateStore (currentBag);
					genRep.incLuggageAtSR();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				currentBag = null;
				nextState = EPorterStates.AT_THE_PLANES_HOLD;
				break;
			}	
			state = nextState;
			try {
				genRep.updatePorterState(state);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Porter Dying!\n");
	}	
}
