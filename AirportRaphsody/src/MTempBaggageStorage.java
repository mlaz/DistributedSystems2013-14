import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 * 
 */
public class MTempBaggageStorage {
	private int storedBags;
	private Lock lock;

	public MTempBaggageStorage(MGeneralRepository genRep) {
		genRep.setTempBaggageStorage(this);
		storedBags = 0;
		lock = new ReentrantLock();
	}

	/**
	 * @param currentBag
	 */
	public synchronized void carryItToAppropriateStore(Bag currentBag) {
		lock.lock();
		try {
			storedBags++;

			// System.out.println("Bag from passenger: " + currentBag.getPassNumber() + " stored.\n");
		} finally {
			lock.unlock();
		}
	}
}
