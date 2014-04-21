import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 */

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 * 
 */
public class MDepartureTerminalEntrace {
	private int remainingPassengers;
	private int N;
	private Lock lock;
	private Condition cond;
	
	public MDepartureTerminalEntrace (int N, MGeneralRepository genRep) {
		remainingPassengers = caclNumPassengers(N);
		genRep.setDepartureTerminalEntrace(this);
		this.N = N;
		lock = new ReentrantLock();
		cond = lock.newCondition();
	}
	
	/**
	 * This uses the same algorithm used to generate the passenger threads
	 * @param number of passengers per plane
	 * @return number of expected 
	 */
	private int caclNumPassengers(int N) {
		int n = 0;
		int passNumber;
		Boolean transit = true;
		for (passNumber = 0; passNumber < N; passNumber++) {
			transit = !transit;
			if (transit)
				n++;
		}
		return n;
	}
	
	public void prepareNextLeg() throws InterruptedException {
		lock.lock();
		try {
			remainingPassengers--;
			
			if (remainingPassengers > 0) {
				cond.await();
			} else {
				remainingPassengers = caclNumPassengers(N);
				cond.signalAll();
			}
		} finally {
			lock.unlock();
		}
	}

}
