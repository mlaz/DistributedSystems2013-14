import java.util.LinkedList;
import java.util.Queue;

/**
 * 
 */

/**
 * @author Miguel Azevedo, Filipe Teixeira
 * 
 */
public class MArrivalTerminalTransferZone implements IDriverArrivalTerminalTransferZone, IPassengerArrivalTerminalTransferZone{

	private int nSeats;
	private int passengersToGo;
	private Queue<Integer> busQueue;
	private boolean availableBus = false;

	
	
	/**
	 * @param t
	 * @param genRep
	 */
	public MArrivalTerminalTransferZone(int nSeats, MGeneralRepository genRep) {
		genRep.setArrivalTerminalTransferZone(this);
		this.nSeats = nSeats;
		busQueue = new LinkedList<Integer>();
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
		busQueue.poll();
		if (passengersToGo == 0)
			availableBus = false;
		notifyAll();
	}

	/* (non-Javadoc)
	 * @see IDriverArrivalTerminalTransferZone#announcingBusBoaring()
	 */
	@Override
	public synchronized void announcingBusBoaring() throws InterruptedException {
		passengersToGo = nSeats;
		availableBus = true;
		if (busQueue.isEmpty())
			wait();
		else
			notifyAll();
		System.out.println("DRIVER ANNOUNCED");
	}

}
