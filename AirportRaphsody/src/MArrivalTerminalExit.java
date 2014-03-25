import java.util.LinkedList;
import java.util.Queue;

/**
 * 
 */

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 * 
 */
public class MArrivalTerminalExit implements IDriverArrivalTerminalTransferZone, IPassengerArrivalExitTransferZone {

	private int totalPassengers;
	private int passengersDone = 0;
	private int nSeats;
	private int passengersToGo;
	private Queue<Integer> busQueue;
	private boolean availableBus = false;
	
	
	/**
	 * @param t
	 * @param genRep
	 */
	public MArrivalTerminalExit(int nAirplanes, int nPassengers, int nSeats, MGeneralRepository genRep) {
		genRep.setArrivalTerminalExit(this);
		this.nSeats = nSeats;
		busQueue = new LinkedList<Integer>();
		totalPassengers = nAirplanes * nPassengers;
	}

	/* (non-Javadoc)
	 * @see IPassengerArrivalTerminalTransferZone#takeABus()
	 */
	@Override
	public synchronized void takeABus(int passNumber) throws InterruptedException {
		busQueue.add((Integer)passNumber);
		if (busQueue.size() == nSeats)
			notifyAll();
		
		System.out.println("Passenger " + passNumber + " Qsize" + busQueue.size());
		while ( (passNumber != busQueue.peek()) || (passengersToGo == 0) || (!availableBus) )
			wait();
		
		passengersToGo--;
		//passengersDone++;
		busQueue.poll();
		if (passengersToGo == 0)
			availableBus = false;
		notifyAll();
	}

	/* (non-Javadoc)
	 * @see IDriverArrivalTerminalTransferZone#announcingBusBoaring()
	 */
	@Override
	public synchronized boolean announcingBusBoaring(int lastPassengers) throws InterruptedException {
		passengersDone = passengersDone + lastPassengers;
		System.out.println("passengersDone:" + passengersDone);
		if (totalPassengers == passengersDone)
			return false;// the simulation is over there are no more passengers to process


		passengersToGo = nSeats;
		availableBus = true;

		if (busQueue.isEmpty()) {//if
			wait();
			if (totalPassengers == passengersDone)
				return false;
		}
		else{
			
			int size;
			passengersToGo = ((size = busQueue.size()) < nSeats) ? size : nSeats;
			notifyAll();
		}
		System.out.println("DRIVER ANNOUNCED");
		return true;


	}
	
	/**
	 * 
	 */
	public synchronized void goHome() {
		passengersDone++;
		System.out.println("passengersDone:" + passengersDone);
		if (totalPassengers == passengersDone)
			notify();//so the driver knows
	}

	@Override
	public synchronized void announcingDeparture() {
		availableBus = false;
	}

}
