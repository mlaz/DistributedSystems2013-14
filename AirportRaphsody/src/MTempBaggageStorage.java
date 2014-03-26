/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 *
 */
public class MTempBaggageStorage {
	private int storedBags;

	public MTempBaggageStorage(MGeneralRepository genRep) {
		genRep.setTempBaggageStorage(this);
		storedBags = 0;
	}

	/**
	 * @param currentBag
	 */
	public synchronized void carryItToAppropriateStore(Bag currentBag) {
		storedBags++;
		
		//System.out.println("Bag from passenger: " + currentBag.getPassNumber() + " stored.\n");
	}
}
