import java.util.Iterator;
import java.util.LinkedList;

/**
 * 
 */

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 * 
 */
public class MBaggagePickupZone implements IPorterBaggagePickupZone, IPassengerBaggageCollectionPoint {
	private boolean looseNextItem;
	private int currentFlight;
	private int passengersWaiting;
	private boolean waitingForBags;
	private LinkedList<Integer> conveyourBelt;
	
	/**
	 * @param genRep
	 */
	public MBaggagePickupZone(MGeneralRepository genRep) {
		genRep.setBaggagePickupZone(this);
		conveyourBelt = new LinkedList<Integer>();
		looseNextItem = false;
		passengersWaiting = 0;
		waitingForBags = true;
		currentFlight = 0;
	}

	/* (non-Javadoc)
	 * @see IPorterBaggagePickupZone#placeBag(Bag)
	 */
	@Override
	public synchronized boolean carryItToAppropriateStore(int passId) {
		looseNextItem = !looseNextItem;
		if (looseNextItem)
			return false;
			
		System.out.println("Bag from passenger: " + passId + " droped at BPZ.\n");
		conveyourBelt.add((Integer) passId);
		//waitingForBags = true;
		notifyAll();
		return true;
	}

	/* (non-Javadoc)
	 * @see IPorterBaggagePickupZone#noMoreBags()
	 */
	@Override
	public synchronized void noMoreBagsToCollect() throws InterruptedException {
		System.out.println("no more bags\n");
		waitingForBags = false;
		notifyAll();
		System.out.println("passengersWaiting: "+passengersWaiting);
		while ((passengersWaiting > 0) || (!conveyourBelt.isEmpty()))
			wait();
		waitingForBags = true;
		currentFlight++;
	}

	/* (non-Javadoc)
	 * @see IPassengerBaggageCollectionPoint#tryToCollectABag(int)
	 */
	@Override
	public synchronized boolean tryToCollectABag(int passengerNumber, int flightNum) throws InterruptedException {
		if (currentFlight != flightNum)
			return false;

		passengersWaiting++;
		System.out.println(passengerNumber + " trying to collect a bag! \n");
		Iterator<Integer> i;
		
		while (waitingForBags || (!conveyourBelt.isEmpty())) {
			i= conveyourBelt.iterator();
			while (i.hasNext()) 
				if (i.next() == passengerNumber) {
					i.remove();
					notifyAll();
					passengersWaiting--;
					//System.out.println("passengersWaiting: "+passengersWaiting);
					if(passengersWaiting == 0)
						notify();
					return true;
				}
			wait();	
		}
		
		passengersWaiting--;
		if(passengersWaiting == 0)
			notify();
		//System.out.println("passengersWaiting: "+passengersWaiting);
		return false;
	}

}
