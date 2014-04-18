import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 
 */

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 * 
 */
public class MArrivalTerminalExit implements
		IDriverArrivalTerminalTransferZone, IPassengerArrivalExitTransferZone {

	private int totalPassengers;
	private int passengersDone = 0;
	private int nSeats;
	private int passengersToGo;
	private Queue<Integer> busQueue;
	private boolean availableBus = false;
	private MGeneralRepository genRep;

	/**
	 * @param t
	 * @param genRep
	 */
	public MArrivalTerminalExit(int nAirplanes, int nPassengers, int nSeats,
			MGeneralRepository genRep) {
		genRep.setArrivalTerminalExit(this);
		this.nSeats = nSeats;
		busQueue = new LinkedList<Integer>();
		totalPassengers = nAirplanes * nPassengers;
		this.genRep = genRep;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see IPassengerArrivalTerminalTransferZone#takeABus()
	 */
	@Override
	public synchronized void takeABus(int passNumber)
			throws InterruptedException {
		busQueue.add((Integer) passNumber);
		if (busQueue.size() == nSeats)
			notifyAll(); // the line is enough to fill the bus
		genRep.updateDriverQueue(busQueue.toArray());

		System.out.println("[" + passNumber + " TakeBus] Passengers waiting to enter the bus: " + busQueue.size());
		while ((passNumber != busQueue.peek()) || (passengersToGo == 0)
				|| (!availableBus))
			wait();

		passengersToGo--;
		busQueue.poll();

		genRep.updateDriverQueue(busQueue.toArray());

		if (passengersToGo == 0)
			availableBus = false;
		notifyAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see IDriverArrivalTerminalTransferZone#announcingBusBoaring()
	 */
	@Override
	public synchronized boolean announcingBusBoaring(int lastPassengers) throws InterruptedException {
		
		passengersDone = passengersDone + lastPassengers;
		System.out.println("[Driver] passengersDone:" + passengersDone);
		if (totalPassengers == passengersDone)
			return false;  	/* the simulation is over there are no more passengers
						       to process */

		passengersToGo = nSeats;
		availableBus = true;

		if (busQueue.isEmpty()) { // no one to go
			wait(); // wait for what?
			if (totalPassengers == passengersDone)
				return false; /* the simulation is over there are no more
							     passengers to process */
		} else { // someone to go
			int size;
			passengersToGo = ((size = busQueue.size()) < nSeats) ? size : nSeats;
			notifyAll();
		}
		
		System.out.println("DRIVER ANNOUNCED -> You can come in!");
		return true;
	}

	/**
	 * 
	 */
	public synchronized void goHome(int passengerNumber) {
		passengersDone++;
		System.out.println("[" + passengerNumber + " goHome] passengersDone:" + passengersDone);
		if (totalPassengers == passengersDone)
			notify();// so the driver knows
	}

	@Override
	public synchronized void announcingDeparture() {
		System.out.println("[DRIVER] Im leaving!");
		availableBus = false;
	}

}
