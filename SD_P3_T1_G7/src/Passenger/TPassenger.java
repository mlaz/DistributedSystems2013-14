package Passenger;

import java.rmi.RemoteException;

import Utils.ClockTuple;
import Utils.VectorClock;


/**
 * Class that implements a Passenger.
 * 
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 */
public class TPassenger extends Thread {
	
	/**
	 * The interface of the Passenger with the General Repository
	 */
	private IPassengerGenRep genRep;
	/**
	 * The interface of the Passenger with the Arrival Terminal
	 */
	private IPassengerArrivalTerminal arrivalTerminal;
	/**
	 * The interface of the Passenger with the Baggage Collection Point
	 */
	private IPassengerBaggageCollectionPoint luggageCollectionPoint;
	/**
	 * The interface of the Passenger with the Baggage Reclaim Guichet
	 */
	private IPassengerBaggageReclaimGuichet baggageReclaimOffice;
	/**
	 * The interface of the Passenger with the Arrival Terminal Exit
	 */
	private IPassengerArrivalExitTransferZone arrivalTerminalExit;
	/**
	 * The interface of the Passenger with the Departure Terminal Entrance
	 */
	private IPassengerDepartureTerminalEntrance departureTerminalEntrace;
	/**
	 * The interface of the Passenger with the Bus
	 */
	private IPassengerBus bus;
	
	/**
	 * The ID of this passenger
	 */
	private int passengerNumber;
	/**
	 * The number of flight this passenger is in
	 */
	private int flightNumber;
	/**
	 * The number of bags the passenger still has to collect
	 */
	private int remainingBags;
	/**
	 * True if the passenger is in transit
	 */
	private boolean inTransit;

	/**
	 * The vectorial clock
	 */
	private VectorClock vecClock;
	/**
	 * The index of this passenger on the vectorial clock
	 */
	private int clockIndex;
	
    /**
     * @param passengerNumber The ID of this passenger
     * @param remainingBags The number of bags that the passenger has
     * @param inTransit True if the passenger is in transit
     * @param flightNumber The number of the flight this passenger is in
     * @param numIdentities The number of identities that use the vectorial clock
     * @param clockIndex The index of this passenger on the vectorial clock
     * @param genRep The interface with the General Repository
     * @param arrivalTerminal The interface with the Arrival Terminal
     * @param luggageCollectionPoint The interface with the Luggage Collection Point
     * @param baggageReclaimOffice The interface with the Baggage Reclaim Office
     * @param arrivalTerminalExit The interface with the Arrival Terminal Exit
     * @param departureTerminalEntrace The interface with the Departure Terminal Entrance
     * @param bus The interface with the Bus
     */
    public TPassenger(int passengerNumber, 
    		int remainingBags, 
			boolean inTransit,
			int flightNumber,
			int numIdentities,	//to initialize the vector clock
			int clockIndex,		//index in the clock
			IPassengerGenRep genRep,
			IPassengerArrivalTerminal arrivalTerminal,
    		IPassengerBaggageCollectionPoint luggageCollectionPoint,
    		IPassengerBaggageReclaimGuichet baggageReclaimOffice,
    		IPassengerArrivalExitTransferZone arrivalTerminalExit,
    		IPassengerDepartureTerminalEntrance departureTerminalEntrace,
    		IPassengerBus bus) {
    	
		this.genRep = genRep;
		this.vecClock = new VectorClock(numIdentities);
		this.clockIndex = clockIndex;
		this.passengerNumber = passengerNumber;
		if (remainingBags < 0)
			remainingBags = 0;
		this.remainingBags = remainingBags;
		this.inTransit = inTransit;		
		this.arrivalTerminal = arrivalTerminal;
		this.luggageCollectionPoint = luggageCollectionPoint;
		this.baggageReclaimOffice = baggageReclaimOffice;
		this.arrivalTerminalExit = arrivalTerminalExit;
		this.departureTerminalEntrace = departureTerminalEntrace;
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
		VectorClock retClock = null;
		ClockTuple<Boolean> clockBool = null;
		while (running) {
			switch (state) {
			case AT_THE_DISEMBARKING_ZONE:
				System.out.println("PassengerNumber" + passengerNumber + " AT_THE_DISEMBARKING_ZONE\n");
				try {
					vecClock.increment(clockIndex);
					retClock = this.arrivalTerminal.whatSouldIDo(passengerNumber, vecClock);
					vecClock.updateClock(retClock);	
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RemoteException e) {
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

				System.out.println("PassengerNumber" + passengerNumber + " AT_THE_LUGGAGE_COLLECTION_POINT\n");
				try {
					
					while (remainingBags > 0) {
						vecClock.increment(clockIndex);
						clockBool = luggageCollectionPoint.tryToCollectABag(passengerNumber, flightNumber, vecClock);
						vecClock.updateClock(clockBool.getClock());
						if( !clockBool.getData() ) {
							break;
						}
						remainingBags--;
						genRep.gotLuggage(passengerNumber, vecClock);
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
				System.out.println("PassengerNumber" + passengerNumber + " AT_THE_BAGGAGE_RECLAIM_OFFICE\n");
				try {
					vecClock.increment(clockIndex);
					retClock = baggageReclaimOffice.reclaimBags(passengerNumber, vecClock);
					vecClock.updateClock(retClock);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				nextState = EPassengerStates.EXITING_THE_ARRIVAL_TERMINAL;
				
			case EXITING_THE_ARRIVAL_TERMINAL:
				System.out.println("PassengerNumber" + passengerNumber + " EXITING_THE_ARRIVAL_TERMINAL\n");
				try {
					vecClock.increment(clockIndex);
					retClock = arrivalTerminalExit.goHome(passengerNumber, vecClock);
					vecClock.updateClock(retClock);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				running = false;
				break;
				
			case AT_THE_ARRIVAL_TRANSFER_TERMINAL:
				System.out.println(passengerNumber + " AT_THE_ARRIVAL_TRANSFER_TERMINAL\n");
				try {
					try {
						vecClock.increment(clockIndex);
						retClock = arrivalTerminalExit.takeABus(passengerNumber, vecClock);
						vecClock.updateClock(retClock);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				nextState = EPassengerStates.TERMINAL_TRANSFER;
				break;
				
			case TERMINAL_TRANSFER:
				System.out.println(passengerNumber + " TERMINAL_TRANSFER\n");

				try {
					vecClock.increment(clockIndex);
					clockBool = bus.enterTheBus(passengerNumber, vecClock);
					vecClock.updateClock(clockBool.getClock());
					if(!clockBool.getData())
						nextState = EPassengerStates.AT_THE_ARRIVAL_TRANSFER_TERMINAL;
					else
						nextState = EPassengerStates.AT_THE_DEPARTURE_TRANSFER_TERMINAL;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;
				
			case AT_THE_DEPARTURE_TRANSFER_TERMINAL:
				System.out.println(passengerNumber + " AT_THE_DEPARTURE_TRANSFER_TERMINAL\n");

				try {
					try {
						vecClock.increment(clockIndex);
						retClock = bus.leaveTheBus(passengerNumber, vecClock);
						vecClock.updateClock(retClock);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				nextState = EPassengerStates.ENTERING_THE_DEPARTURE_TERMINAL;
				break;
				
			case ENTERING_THE_DEPARTURE_TERMINAL:
				System.out.println(passengerNumber + " ENTERING_THE_DEPARTURE_TERMINAL\n");
				try {
				    try {
				    	vecClock.increment(clockIndex);
						retClock = departureTerminalEntrace.prepareNextLeg(vecClock);
						vecClock.updateClock(retClock);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (InterruptedException e) {
				    // TODO Auto-generated catch block
				    e.printStackTrace();
				}
				running = false;
				break;
			}	
			state = nextState;
			if (running)
				try {
					genRep.setPassengerStat(passengerNumber, state, vecClock);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		System.out.println("Passenger #" + passengerNumber + " dying\n");
	}

	/**
	 * @return The ID of this passenger
	 */
	public int getPassNumber() {
		// TODO Auto-generated method stub
		return passengerNumber;
	}	

}
