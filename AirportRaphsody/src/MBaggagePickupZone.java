import java.util.Iterator;
import java.util.LinkedList;

/**
 * 
 */

/**
 * @author Miguel Azevedo, Filipe Teixeira
 * 
 */
public class MBaggagePickupZone implements IPorterBaggagePickupZone, IPassengerBaggageCollectionPoint{
	private int passengersWaiting = 0;
	private boolean waitingForBags = true;
	private LinkedList<Integer> conveyourBelt;
	
	/**
	 * @param genRep
	 */
	public MBaggagePickupZone(MGeneralRepository genRep) {
		genRep.setBaggagePickupZone(this);
		conveyourBelt = new LinkedList<Integer>();
	}

	/* (non-Javadoc)
	 * @see IPorterBaggagePickupZone#placeBag(Bag)
	 */
	@Override
	public synchronized void carryItToAppropriateStore(int passId) {
		System.out.println("Bag from passenger: " + passId + " droped at BPZ.\n");
		conveyourBelt.add((Integer) passId);
		waitingForBags = true;
		notifyAll();
	}

	/* (non-Javadoc)
	 * @see IPorterBaggagePickupZone#noMoreBags()
	 */
	@Override
	public synchronized void noMoreBagsToCollect() throws InterruptedException {
		System.out.println("no more bags\n");
		waitingForBags = false;
		notifyAll();		
		while (passengersWaiting < 0)
			wait();
		waitingForBags = true;
	}

	/* (non-Javadoc)
	 * @see IPassengerBaggageCollectionPoint#tryToCollectABag(int)
	 */
	@Override
	public synchronized boolean tryToCollectABag(int passengerNumber, int flightNum) throws InterruptedException {
		passengersWaiting++;
		System.out.println(passengerNumber + " trying to collect a bag! \n");
		Iterator<Integer> i;
		
		while (waitingForBags /*|| !conveyourBelt.isEmpty()||currentFlight == flightNum*/) {
			i= conveyourBelt.iterator();
			while (i.hasNext()) 
				if (i.next() == passengerNumber) {
					i.remove();
					/*if ((!waitingForBags) && conveyourBelt.isEmpty()) 
						currentFlight++; //last bag from currentFlight
					System.out.println("currentFlight: " + currentFlight);*/
					passengersWaiting--;
					return true;
				}
			wait();	
		}
		passengersWaiting--;
		//System.out.println("myFlight: " + flightNum);
		//System.out.println("currentFlight: " + currentFlight +" returning FALSE");
		return false;
	}


}
