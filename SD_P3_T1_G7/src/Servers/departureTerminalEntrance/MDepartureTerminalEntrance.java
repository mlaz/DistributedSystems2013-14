package Servers.departureTerminalEntrance;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/**
 * 
 */

import Utils.VectorClock;

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 * Monitor da entrada do terminal de partida
 */
public class MDepartureTerminalEntrance implements IDepartureTerminalEntrance {
	private int remainingPassengers;
	private int totalPassengers;
	private Lock lock;
	private Condition cond;
	private VectorClock vecClock;
	
    /**
     *
     * @param totalPassengers
     */
    public MDepartureTerminalEntrance (int totalPassengers, int numEntities) {
		remainingPassengers = caclNumPassengers(totalPassengers);
		this.totalPassengers = totalPassengers;
		lock = new ReentrantLock();
		cond = lock.newCondition();
		this.vecClock = new VectorClock ( numEntities ); 
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

    /**
     *
     * @throws InterruptedException
     */
    public VectorClock prepareNextLeg(VectorClock extClk) throws InterruptedException {
		lock.lock();
		try {
			vecClock.updateClock(extClk);
			remainingPassengers--;
			
			if (remainingPassengers > 0) {
				cond.await();
			} else {
				remainingPassengers = caclNumPassengers(totalPassengers);
				cond.signalAll();
			}
			return vecClock;
		} finally {
			lock.unlock();
		}
	}

}
