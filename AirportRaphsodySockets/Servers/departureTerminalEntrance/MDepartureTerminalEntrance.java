package Servers.departureTerminalEntrance;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import Servers.clientsInterfaces.IPassengerDepartureTreminalEntrance;

/**
 * 
 */

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 * Monitor da entrada do terminal de partida
 */
public class MDepartureTerminalEntrance implements IPassengerDepartureTreminalEntrance {
	private int remainingPassengers;
	private int totalPassengers;
	private Lock lock;
	private Condition cond;

    /**
     *
     * @param totalPassengers
     */
    public MDepartureTerminalEntrance (int totalPassengers) {
		remainingPassengers = caclNumPassengers(totalPassengers);
		this.totalPassengers = totalPassengers;
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

    /**
     *
     * @throws InterruptedException
     */
    public void prepareNextLeg() throws InterruptedException {
		lock.lock();
		try {
			remainingPassengers--;
			
			if (remainingPassengers > 0) {
				cond.await();
			} else {
				remainingPassengers = caclNumPassengers(totalPassengers);
				cond.signalAll();
			}
		} finally {
			lock.unlock();
		}
	}

}
