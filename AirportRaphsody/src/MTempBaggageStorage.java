/**
 * @author Miguel Azevedo, Filipe Teixeira
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
	public void carryItToAppropriateStore(Bag currentBag) {
		storedBags++;
		// TODO Auto-generated method stub
		//System.out.println("Bag from passenger: " + currentBag.getPassNumber() + " stored.\n");
	}

}
