package Servers.tempStorage;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import Utils.Bag;
import Utils.VectorClock;
/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 * Monitor para o depósito temporário de bagagens
 */
public class MTempBaggageStorage implements ITempStorage {	
	private int storedBags;
	private Lock lock;
	private VectorClock vecClock;

    /**
     *
     */
    public MTempBaggageStorage(int numEntities) {
		storedBags = 0;
		lock = new ReentrantLock();
		this.vecClock = new VectorClock(numEntities);
	}

	/**
	 * @param currentBag
	 */
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
