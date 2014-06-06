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
import Passenger.IPassengerArrivalTerminal;
import Porter.IPorterArrivalTerminal;

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 * Monitor terminal de chegada
 */
public class MArrivalTerminal implements IArrivalTerminal {

	private Queue<Stack<Bag>> flightQueue;
	private Stack<Bag> currentPlanesHold;
	private boolean ongoingArrival;
	private int passengersPerPlane;
	private int remainingPassengers;
	private Lock lock;
	private Condition passengers;	//give a better name to this
	private VectorClock vecClock;
    /**
     *
     * @param nFlights
     * @param nPassengers
     * @param maxBags
     */
    public MArrivalTerminal(int nFlights, int nPassengers, int maxBags) {
		//generating bags
		int i;
		int j;
		int k;
		int nbags = maxBags;
		Boolean transit = true;
		Stack<Bag> planesHold;
		flightQueue = new LinkedList <Stack<Bag>>();
		for(k = 0; k < nFlights; k++) {
			planesHold = new Stack<Bag>();
			for (i = 0; i < nPassengers; i++) {
				for (j = 0 ; j < nbags; j++)
					planesHold.add(new Bag(i,transit));
			
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
	
	/**
     * @return 
     * @throws java.lang.InterruptedException
	 * @see IPorterArrivalTerminal#takeARest
	 * ()
	 */
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

	/**
     * @throws java.lang.InterruptedException
	 * @see IPassengerArrivalTerminal#whatSouldIDo()
	 */
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

    /**
     *
     * @return
     */
    public ClockTuple<Bag> tryToCollectABag (VectorClock extClk) {
		lock.lock();
		try {
			vecClock.updateClock(extClk);
			if (currentPlanesHold.isEmpty())
				return null;
			return new ClockTuple<Bag>(currentPlanesHold.pop(), vecClock);
		} finally {
			lock.unlock();
		}
	}

}
