package Servers.baggageReclaimGuichet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import Passenger.IPassengerBaggageReclaimGuichet;

/**
 * 
 */

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 * 
 */
public class MBaggageReclaimGuichet implements IPassengerBaggageReclaimGuichet {

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
