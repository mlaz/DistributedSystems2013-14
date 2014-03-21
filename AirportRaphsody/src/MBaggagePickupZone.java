/**
 * 
 */

/**
 * @author Miguel Azevedo, Filipe Teixeira
 * 
 */
public class MBaggagePickupZone implements IPorterBaggagePickupZone{
	boolean waitingForBags = true;
	/* (non-Javadoc)
	 * @see IPorterBaggagePickupZone#placeBag(Bag)
	 */
	@Override
	public synchronized void carryItToAppropriateStore(Bag currentBag) {
		System.out.println("Bag from passenger: " + currentBag.getPassNumber() + " droped at BPZ.\n");
		//lastDroppedBag = currentBag;
		//waitingForBags = true;
		//notify();
	}

	/* (non-Javadoc)
	 * @see IPorterBaggagePickupZone#noMoreBags()
	 */
	@Override
	public synchronized void noMoreBagsToCollect() {
		System.out.println("no more bags\n");
		//waitingForBags = false;
		//notify();		
	}


}
