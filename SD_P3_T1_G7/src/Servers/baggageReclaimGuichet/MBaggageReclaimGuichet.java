package Servers.baggageReclaimGuichet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 * Monitor do guichet de reclamação de bagagens 
 */
public class MBaggageReclaimGuichet implements IBaggageReclaimGuichet {

	private Lock lock;
	
	/**
	 */
	public MBaggageReclaimGuichet() {
		lock = new ReentrantLock();
	}

	/**
	 * 
     * @param passengerNumber
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
