package Servers.arrivalTerminal;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import Utils.Bag;
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
					planesHold.add(new Bag(i,true /*transit*/));
			
				transit = !transit;
				nbags = (nbags == maxBags) ? 0 : nbags + 1;
			}
			System.out.println("Flight# " + k + " NBAGS:" + planesHold.size());
			flightQueue.add(planesHold);
		}
		
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
	public boolean takeARest() throws InterruptedException {
		lock.lock();
		try {
			if (ongoingArrival) {
				
				while (remainingPassengers > 0) {
					passengers.await();
				}
				
				currentPlanesHold = flightQueue.poll();
				remainingPassengers = passengersPerPlane;
			}
			ongoingArrival = !(flightQueue.isEmpty());
			
			return !currentPlanesHold.isEmpty();
			
		} finally {
			lock.unlock();
		}
	}

	/**
     * @throws java.lang.InterruptedException
	 * @see IPassengerArrivalTerminal#whatSouldIDo()
	 */
	@Override
	public void whatSouldIDo(int passengerId) throws InterruptedException {
		lock.lock();
		
		try {
			//System.out.println("What should I do?: "+ passengerId + "\n");
			remainingPassengers--;
			if (remainingPassengers == 0) {
				ongoingArrival = false;
				passengers.signal();
			}
			
		} finally {
			lock.unlock();
		}
	}

    /**
     *
     * @return
     */
    public Bag tryToCollectABag () {
		lock.lock();
		try {
			if (currentPlanesHold.isEmpty())
				return null;
			return currentPlanesHold.pop();
		} finally {
			lock.unlock();
		}
	}

}
