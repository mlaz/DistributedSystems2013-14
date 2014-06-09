package Servers.departureTerminalEntrance;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/**
 * 
 */

import Utils.VectorClock;

/**
 * Class that implements the Departure Terminal Entrance server services.
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 */
public class MDepartureTerminalEntrance implements IDepartureTerminalEntrance {
	/**
	 * The number of passengers that are or will be in transit
	 */
	private int remainingPassengers;
	/**
	 * The total number of passengers of the current simulation
	 */
	private final int totalPassengers;
	/**
	 * The lock
	 */
	private Lock lock;
	/**
	 * A waiting condition
	 */
	private Condition cond;
	/**
	 * The Clock
	 */
	private VectorClock vecClock;
	
    
    
    /**
     * Instanciates a Departure Terminal Entrance
     * @param totalPassengers The total number of passengers of the current simulation 
     * @param numEntities The number of entities that will use the VectorClock at the same time
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
	 * @param N number of passengers per plane
	 * @return Number of expected passengers in transit 
	 */
	private int caclNumPassengers(int N) {
		int n = 0;
		int passNumber;
		Boolean transit = true;
		for (passNumber = 0; passNumber < N; passNumber++) {
			if (transit)
				n++;
			transit = !transit;
		}
		return n;
	}

    public VectorClock prepareNextLeg(VectorClock extClk) throws InterruptedException {
		lock.lock();
		try {
			System.out.print("PREPARE NEXT LEG >>> RemainingPassengers:" + remainingPassengers);
			vecClock.updateClock(extClk);
			remainingPassengers--;
			System.out.println(" ...... " + remainingPassengers);
			
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
