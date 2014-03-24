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
	
	private MGeneralRepository genRep;
	private IPassengerArrivalTerminal arrivalTerminal;
	private IPassengerBaggageCollectionPoint luggageCollectionPoint;
	private MBaggageReclaimGuichet baggageReclaimOffice;
	private IPassengerArrivalExitTransferZone arrivalTerminalExit;
	private IPassengerBus bus;
	
	private int passengerNumber;
	private int remainingBags;
	private boolean inTransit;

	public TPassenger(int passengerNumber, 
			int remainingBags, 
			boolean inTransit, 
			MGeneralRepository genRep) {
		this.genRep = genRep;
		
		this.passengerNumber = passengerNumber;
		if (remainingBags < 0)
			remainingBags = 0;
		this.remainingBags = remainingBags;
		this.inTransit = inTransit;		
		this.arrivalTerminal = genRep.getArrivalTerminal();
		this.luggageCollectionPoint = genRep.getBaggagePickupZone();
		this.baggageReclaimOffice = genRep.getBaggageReclaimGuichet();
		this.arrivalTerminalExit = genRep.getArrivalTerminalExit();
		this.bus = genRep.getBus();
	}
	
	public void run() {
		
		states state = states.AT_THE_DISEMBARKING_ZONE;
		states nextState = state;
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
					nextState = states.AT_THE_ARRIVAL_TRANSFER_TERMINAL;
				else { 
					if (remainingBags > 0)
						nextState = states.AT_THE_LUGGAGE_COLLECTION_POINT;
					else
						nextState = states.EXITING_THE_ARRIVAL_TERMINAL;
				}	
				break;
				
			case AT_THE_LUGGAGE_COLLECTION_POINT:
				try {
					
					while ( (remainingBags > 0) && luggageCollectionPoint.tryToCollectABag(passengerNumber) ) {
						remainingBags--;
						System.out.println("Passenger #" + passengerNumber + " just got a bag\n");
					}
						
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if (remainingBags > 0)
					nextState = states.AT_THE_BAGGAGE_RECLAIM_OFFICE;
				else 
					nextState = states.EXITING_THE_ARRIVAL_TERMINAL;
				break;
				
			case AT_THE_BAGGAGE_RECLAIM_OFFICE:
				baggageReclaimOffice.reclaimBags(passengerNumber);
				nextState = states.EXITING_THE_ARRIVAL_TERMINAL;
				
			case EXITING_THE_ARRIVAL_TERMINAL:
				arrivalTerminalExit.goHome();
				running = false;
				break;
				
			case AT_THE_ARRIVAL_TRANSFER_TERMINAL:
				System.out.println(passengerNumber + " AT_THE_ARRIVAL_TRANSFER_TERMINAL\n");
				try {
					arrivalTerminalExit.takeABus(passengerNumber);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				nextState = states.TERMINAL_TRANSFER;
				break;
				
			case TERMINAL_TRANSFER:
				System.out.println(passengerNumber + " TERMINAL_TRANSFER\n");

				try {
					if (bus.enterTheBus())
						nextState = states.AT_THE_DEPARTURE_TRANSFER_TERMINAL;
					else
						nextState = states.AT_THE_ARRIVAL_TRANSFER_TERMINAL;

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
				
			case AT_THE_DEPARTURE_TRANSFER_TERMINAL:
				System.out.println(passengerNumber + " AT_THE_DEPARTURE_TRANSFER_TERMINAL\n");

				try {
					bus.leaveTheBus();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				nextState = states.ENTERING_THE_DEPARTURE_TERMINAL;
				break;
				
			case ENTERING_THE_DEPARTURE_TERMINAL:
				System.out.println(passengerNumber + " AT_THE_DEPARTURE_TRANSFER_TERMINAL\n");

				running = false;
				break;
			}	
			state = nextState;
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
