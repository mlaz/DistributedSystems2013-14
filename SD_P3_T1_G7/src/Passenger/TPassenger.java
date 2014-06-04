package Passenger;

import java.rmi.RemoteException;

/**
 * 
 */

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 * Classe TPassanger: classe de implementação da thread de passageiro
 */
public class TPassenger extends Thread {
	
	private IPassengerGenRep genRep;
	private IPassengerArrivalTerminal arrivalTerminal;
	private IPassengerBaggageCollectionPoint luggageCollectionPoint;
	private IPassengerBaggageReclaimGuichet baggageReclaimOffice;
	private IPassengerArrivalExitTransferZone arrivalTerminalExit;
	private IPassengerBus bus;
	
	private int passengerNumber;
	private int flightNumber;
	private int remainingBags;
	private boolean inTransit;

    /**
     * Construtor da classe
     * @param passengerNumber
     * @param remainingBags
     * @param inTransit
     * @param flightNumber
     * @param genRep
     */
    public TPassenger(int passengerNumber, 
    		int remainingBags, 
			boolean inTransit,
			int flightNumber,
			IPassengerGenRep genRep,
			IPassengerArrivalTerminal arrivalTerminal,
    		IPassengerBaggageCollectionPoint luggageCollectionPoint,
    		IPassengerBaggageReclaimGuichet baggageReclaimOffice,
    		IPassengerArrivalExitTransferZone arrivalTerminalExit,
    		IPassengerBus bus) {
		this.genRep = genRep;
		
		this.passengerNumber = passengerNumber;
		if (remainingBags < 0)
			remainingBags = 0;
		this.remainingBags = remainingBags;
		this.inTransit = inTransit;		
		this.arrivalTerminal = arrivalTerminal;
		this.luggageCollectionPoint = luggageCollectionPoint;
		this.baggageReclaimOffice = baggageReclaimOffice;
		this.arrivalTerminalExit = arrivalTerminalExit;
		this.bus = bus;
		this.flightNumber = flightNumber;
		//System.out.println(passengerNumber + "fn"+ flightNumber+ "bags" + remainingBags );
		try {
			genRep.registerPassenger(passengerNumber, flightNumber, inTransit, remainingBags);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    /**
     * Implementação da máquina de estados
     */
    public void run() {
		EPassengerStates state = EPassengerStates.AT_THE_DISEMBARKING_ZONE;
		EPassengerStates nextState = state;
		boolean running = true;
		while (running) {
			switch (state) {
			case AT_THE_DISEMBARKING_ZONE:
				try {
					this.arrivalTerminal.whatSouldIDo(passengerNumber);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if (inTransit)
					nextState = EPassengerStates.AT_THE_ARRIVAL_TRANSFER_TERMINAL;
				else { 
					if (remainingBags > 0)
						nextState = EPassengerStates.AT_THE_LUGGAGE_COLLECTION_POINT;
					else
						nextState = EPassengerStates.EXITING_THE_ARRIVAL_TERMINAL;
				}	
				break;
				
			case AT_THE_LUGGAGE_COLLECTION_POINT:
				try {
					
					while ( (remainingBags > 0) && luggageCollectionPoint.tryToCollectABag(passengerNumber, flightNumber) ) {
						remainingBags--;
						genRep.gotLuggage(passengerNumber);
						System.out.println("Passenger #" + passengerNumber + " just got a bag\n");
					}
						
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if (remainingBags > 0)
					nextState = EPassengerStates.AT_THE_BAGGAGE_RECLAIM_OFFICE;
				else 
					nextState = EPassengerStates.EXITING_THE_ARRIVAL_TERMINAL;
				break;
				
			case AT_THE_BAGGAGE_RECLAIM_OFFICE:
				baggageReclaimOffice.reclaimBags(passengerNumber);
				nextState = EPassengerStates.EXITING_THE_ARRIVAL_TERMINAL;
				
			case EXITING_THE_ARRIVAL_TERMINAL:
				arrivalTerminalExit.goHome(passengerNumber);
				running = false;
				break;
				
			case AT_THE_ARRIVAL_TRANSFER_TERMINAL:
				//System.out.println(passengerNumber + " AT_THE_ARRIVAL_TRANSFER_TERMINAL\n");
				try {
					arrivalTerminalExit.takeABus(passengerNumber);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				nextState = EPassengerStates.TERMINAL_TRANSFER;
				break;
				
			case TERMINAL_TRANSFER:
				//System.out.println(passengerNumber + " TERMINAL_TRANSFER\n");

				try {
					if(!bus.enterTheBus(passengerNumber))
						nextState = EPassengerStates.AT_THE_ARRIVAL_TRANSFER_TERMINAL;
					else
						nextState = EPassengerStates.AT_THE_DEPARTURE_TRANSFER_TERMINAL;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;
				
			case AT_THE_DEPARTURE_TRANSFER_TERMINAL:
				//System.out.println(passengerNumber + " AT_THE_DEPARTURE_TRANSFER_TERMINAL\n");

				try {
					bus.leaveTheBus(passengerNumber);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				nextState = EPassengerStates.ENTERING_THE_DEPARTURE_TERMINAL;
				break;
				
			case ENTERING_THE_DEPARTURE_TERMINAL:
				//System.out.println(passengerNumber + " ENTERING_THE_DEPARTURE_TERMINAL\n");
				running = false;
				break;
			}	
			state = nextState;
			if (running)
				try {
					genRep.setPassengerStat(passengerNumber, state);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		System.out.println("Passenger #" + passengerNumber + " dying\n");
	}

	/**
	 * @return
	 */
	public int getPassNumber() {
		// TODO Auto-generated method stub
		return passengerNumber;
	}	

}
