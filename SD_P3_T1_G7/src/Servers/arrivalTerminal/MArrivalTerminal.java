package Servers.arrivalTerminal;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import Utils.Bag;
import Utils.ClockTuple;
import Utils.VectorClock;

/**
 * Class that implements the Arrival Terminal server services.
 * 
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 */
public class MArrivalTerminal implements IArrivalTerminal {

	/**
	 * 
	 */
	private Queue<Stack<Bag>> flightQueue;
	/**
	 * The bags currently on the plane's hold
	 */
	private Stack<Bag> currentPlanesHold;
	/**
	 * 
	 */
	private boolean ongoingArrival;
	/**
	 * The number of passengers in each plane
	 */
	private int passengersPerPlane;
	/**
	 * The number of passengers on the arrival terminal
	 */
	private int remainingPassengers;
	/**
	 * The lock
	 */
	private Lock lock;
	/**
	 * Waiting condition
	 */
	private Condition passengers;
	/**
	 * The clock
	 */
	private VectorClock vecClock;
	
    /**
     * Instanciates a MArrivalTerminal object.
     * @param nFlights The total number of flights on the current simulation
     * @param nPassengers The number of passengers on each plan
     * @param maxBags The maximum number of bags each passenger may have
     */
    public MArrivalTerminal(int nFlights, int nPassengers, int maxBags) {
		//generating bags
		int i;
		int j;
		int k;
		int nbags = maxBags;
		Boolean transit;
		Stack<Bag> planesHold;
		flightQueue = new LinkedList <Stack<Bag>>();
		for(k = 0; k < nFlights; k++) {
			planesHold = new Stack<Bag>();
			transit = true;
			
			for (i = 0; i < nPassengers; i++) {
				for (j = 0 ; j < nbags; j++) {
					planesHold.add(new Bag(i,transit));
				}
				transit = !transit;
				nbags = (nbags == maxBags) ? 0 : nbags + 1;
			}
			System.out.println("Flight# " + k + " NBAGS:" + planesHold.size());
			flightQueue.add(planesHold);
		}
		
		this.vecClock = new VectorClock(nPassengers+2);
		
		ongoingArrival = true;
		passengersPerPlane = remainingPassengers = nPassengers;
		lock = new ReentrantLock();
		passengers = lock.newCondition();
	}
	
	@Override
	public ClockTuple<Boolean> takeARest(VectorClock extClk) throws InterruptedException {
		lock.lock();
		try {
			vecClock.updateClock(extClk);
			if (ongoingArrival) {
				
				while (remainingPassengers > 0) {
					passengers.await();
				}
				
				currentPlanesHold = flightQueue.poll();
				remainingPassengers = passengersPerPlane;
			}
			ongoingArrival = !(flightQueue.isEmpty());
			
			return new ClockTuple<Boolean>(!currentPlanesHold.isEmpty(), vecClock);
			
		} finally {
			lock.unlock();
		}
	}

	@Override
	public VectorClock whatSouldIDo(int passengerId, VectorClock extClk) throws InterruptedException {
		lock.lock();
		
		try {
			vecClock.updateClock(extClk);
			//System.out.println("What should I do?: "+ passengerId + "\n");
			remainingPassengers--;
			if (remainingPassengers == 0) {
				ongoingArrival = false;
				passengers.signal();
			}
			return vecClock;
		} finally {
			lock.unlock();
		}
	}

	@Override
    public ClockTuple<Bag> tryToCollectABag (VectorClock extClk) {
		lock.lock();
		try {
			vecClock.updateClock(extClk);
			if (currentPlanesHold.isEmpty())
				return new ClockTuple<Bag>(null, vecClock);
			return new ClockTuple<Bag>(currentPlanesHold.pop(), vecClock);
		} finally {
			lock.unlock();
		}
	}

}
