package Servers.baggageReclaimGuichet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import Utils.VectorClock;

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 * Monitor do guichet de reclamação de bagagens 
 */
public class MBaggageReclaimGuichet implements IBaggageReclaimGuichet {

	private Lock lock;
	private VectorClock vecClock;
	/**
	 */
	public MBaggageReclaimGuichet( int numEntities ) {
		lock = new ReentrantLock();
		this.vecClock = new VectorClock(numEntities);
	}

	/**
	 * 
     * @param passengerNumber
	 */
	public VectorClock reclaimBags(int passengerNumber, VectorClock extClk) {
		lock.lock();
		
		try{
			vecClock.updateClock(extClk);
			System.out.printf("[%d] reclaiming Bags\n", passengerNumber);
			return vecClock;
		} finally {
			lock.unlock();
		}
	}
}
