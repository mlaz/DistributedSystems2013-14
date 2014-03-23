/**
 * 
 */

/**
 * @author Miguel Azevedo, Filipe Teixeira
 * 
 */
public class MBaggagePickupZone implements IPorterBaggagePickupZone, IPassengerBaggageCollectionPoint{
	private boolean waitingForBags = true;
	private Bag lastDroppedBag = null;
	
	/**
	 * @param genRep
	 */
	public MBaggagePickupZone(MGeneralRepository genRep) {
		genRep.setBaggagePickupZone(this);
	}

	/* (non-Javadoc)
	 * @see IPorterBaggagePickupZone#placeBag(Bag)
	 */
	@Override
	public synchronized void carryItToAppropriateStore(Bag currentBag) {
		System.out.println("Bag from passenger: " + currentBag.getPassNumber() + " droped at BPZ.\n");
		lastDroppedBag = currentBag;
		waitingForBags = true;
		notifyAll();
	}

	/* (non-Javadoc)
	 * @see IPorterBaggagePickupZone#noMoreBags()
	 */
	@Override
	public synchronized void noMoreBagsToCollect() {
		System.out.println("no more bags\n");
		waitingForBags = false;
		notifyAll();		
	}

	/* (non-Javadoc)
	 * @see IPassengerBaggageCollectionPoint#tryToCollectABag(int)
	 */
	@Override
	public synchronized boolean tryToCollectABag(int passengerNumber) throws InterruptedException {
		// TODO Auto-generated method stub
		System.out.println(passengerNumber + " trying to collect a bag! \n");
		
		if (lastDroppedBag == null) 
			wait();
		
		while ( (lastDroppedBag.getPassNumber() != passengerNumber) && waitingForBags )
			wait();
		
		if (lastDroppedBag.getPassNumber() == passengerNumber)
			return true;
		
		return false;
	}


}
