package Servers.tempStorage;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import Utils.Bag;
/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 * Monitor para o depósito temporário de bagagens
 */
public class MTempBaggageStorage implements IBaggageReclaimGuichet {	
	private int storedBags;
	private Lock lock;

    /**
     *
     */
    public MTempBaggageStorage() {
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
