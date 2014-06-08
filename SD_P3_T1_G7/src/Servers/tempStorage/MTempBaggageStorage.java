package Servers.tempStorage;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import Utils.Bag;
import Utils.VectorClock;
/**
 * Class that implements the Temporary Baggage Storage server services.
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 */
public class MTempBaggageStorage implements ITempStorage {	
	/**
	 * The number of bags currently stored
	 */
	private int storedBags;
	/**
	 * The lock.
	 */
	private Lock lock;
	/**
	 * The VectorClock
	 */
	private VectorClock vecClock;

    /**
     * Instanciates a Temporary Baggage Storage object.
     * @param numEntities The number of entities that will use the VectorClock at the same time
     */
    public MTempBaggageStorage(int numEntities) {
		storedBags = 0;
		lock = new ReentrantLock();
		this.vecClock = new VectorClock(numEntities);
	}

	public VectorClock carryItToAppropriateStore(Bag currentBag, VectorClock extClk) {
		lock.lock();
		try {
			vecClock.updateClock(extClk);
			storedBags = storedBags + 1;

			// System.out.println("Bag from passenger: " + currentBag.getPassNumber() + " stored.\n");
			
			return vecClock;
		} finally {
			lock.unlock();
		}
	}
}
