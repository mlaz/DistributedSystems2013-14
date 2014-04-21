import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 */

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 * 
 */
public class MBaggageReclaimGuichet {

	private Lock lock;
	
	/**
	 * @param genRep
	 */
	public MBaggageReclaimGuichet(MGeneralRepository genRep) {
		genRep.setBaggageReclaimGuichet(this);
		lock = new ReentrantLock();
	}

	/**
	 * 
	 */
	public void reclaimBags(int passengerNumber) {
		lock.lock();
		try{
		System.out.printf("[%d] reclaiming Bags\n", passengerNumber);
		} finally {
			lock.unlock();
		}
	}
}
