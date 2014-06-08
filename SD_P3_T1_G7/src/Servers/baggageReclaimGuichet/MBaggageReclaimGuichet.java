package Servers.baggageReclaimGuichet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import Utils.VectorClock;

/**
 * Class that implements the Baggage Reclaim Guichet server services.
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 * @author Miguel Azevedo <lobaoazevedo@ua.pt> 
 */
public class MBaggageReclaimGuichet implements IBaggageReclaimGuichet {

	/**
	 *	The lock 
	 */
	private Lock lock;
	/**
	 * The clock
	 */
	private VectorClock vecClock;
	
	
	/**
	 * Instanciates a Baggage Reclaim Guichet object
	 * @param numEntities The number of entities that will use the VectorClock at the same time
	 */
	public MBaggageReclaimGuichet( int numEntities ) {
		lock = new ReentrantLock();
		this.vecClock = new VectorClock(numEntities);
	}

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
