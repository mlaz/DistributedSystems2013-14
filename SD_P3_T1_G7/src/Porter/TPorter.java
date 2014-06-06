package Porter;

import java.rmi.RemoteException;

import Utils.Bag;
import Utils.ClockTuple;
import Utils.VectorClock;

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 * Classe TPassanger: classe de implementação da thread de passageiro
 */
public class TPorter extends Thread {
	
	private IPorterArrivalTerminal arrivalTerminal;
	private IPorterBaggagePickupZone baggageBeltConveyor;
	private IPorterTempBaggageStorage baggageStorage;
	private IPorterGenRep genRep;
	private int clockIndex;
	private VectorClock vecClock;
	
    /**
     * Construtor da Classe
     * @param genRep
     */
    public TPorter(
    			int numIdentities,
    			int clockIndex,
    			IPorterGenRep genRep, 
    			IPorterArrivalTerminal arrivalTerminal, 
    			IPorterBaggagePickupZone baggageBeltConveyor, 
    			IPorterTempBaggageStorage baggageStorage) {

		this.arrivalTerminal 	 = arrivalTerminal;
		this.baggageBeltConveyor = baggageBeltConveyor;
		this.baggageStorage 	 = baggageStorage;
		this.genRep 		 	 = genRep;
		this.vecClock			 = new VectorClock(numIdentities);
		
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

		EPorterStates state 	= EPorterStates.WAITING_FOR_A_PLANE_TO_LAND;
		EPorterStates nextState = state;
		
		Bag currentBag 	= null;
		boolean running = true;
		
		ClockTuple<Boolean> boolClock;
		ClockTuple<Bag> 	bagClock;
		VectorClock 		retClock;
		
		while (running) {
			switch (state) {
			case WAITING_FOR_A_PLANE_TO_LAND:
				System.out.println("state = WAITING_FOR_A_PLANE_TO_LAND\n");
				try {
					try {
						vecClock.increment(clockIndex);
						genRep.porterWaiting4Plane();
						boolClock = arrivalTerminal.takeARest(vecClock);
						vecClock.updateClock(boolClock.getClock());
						
						if ( boolClock.getData() )
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
					vecClock.increment(clockIndex);
					bagClock = arrivalTerminal.tryToCollectABag (vecClock);
					vecClock.updateClock(bagClock.getClock());
					currentBag = bagClock.getData();
					if ( currentBag == null) {
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
					genRep.removeLuggageAtPlane(vecClock);
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
							vecClock.increment(clockIndex);
							retClock = baggageBeltConveyor.noMoreBagsToCollect(vecClock);
							vecClock.updateClock(retClock);
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
					vecClock.increment(clockIndex);
					boolClock = baggageBeltConveyor.carryItToAppropriateStore (currentBag.getPassNumber(), vecClock);
					vecClock.updateClock(boolClock.getClock());
					if (boolClock.getData())
						try {
							genRep.incLuggageAtCB(vecClock);
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
					vecClock.increment(clockIndex);
					retClock = baggageStorage.carryItToAppropriateStore (currentBag, vecClock);
					vecClock.updateClock(retClock);
					genRep.incLuggageAtSR(vecClock);
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
				genRep.updatePorterState(state, vecClock);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Porter Dying!\n");
	}	
}
