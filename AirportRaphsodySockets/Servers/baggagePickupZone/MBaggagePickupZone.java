package Servers.baggagePickupZone;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import Passenger.IPassengerBaggageCollectionPoint;
import Porter.IPorterBaggagePickupZone;

/**
 * 
 */

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 * 
 */
public class MBaggagePickupZone implements IPorterBaggagePickupZone, IPassengerBaggageCollectionPoint {
	private boolean looseNextItem;
	private int currentFlight;
	private int passengersWaiting;
	private boolean waitingForBags;
	private LinkedList<Integer> conveyourBelt;
	private Lock lock;
	private Condition cond;

	public MBaggagePickupZone() {
		conveyourBelt = new LinkedList<Integer>();
		looseNextItem = false;
		passengersWaiting = 0;
		waitingForBags = true;
		currentFlight = 0;
		lock = new ReentrantLock();
		cond = lock.newCondition();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see IPorterBaggagePickupZone#placeBag(Bag)
	 */
	@Override
	public boolean carryItToAppropriateStore(int passId) {
		lock.lock();
		try {
			looseNextItem = !looseNextItem;
			if (looseNextItem) {
				return false;
			}

			System.out.println("Bag from passenger: " + passId + " droped at BPZ.\n");
			conveyourBelt.add((Integer) passId);
			// waitingForBags = true;
			cond.signalAll();
			return true;
		} finally {
			lock.unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see IPorterBaggagePickupZone#noMoreBags()
	 */
	@Override
	public void noMoreBagsToCollect() throws InterruptedException {
		lock.lock();
		try {
			System.out.println("no more bags\n");
			waitingForBags = false;
			cond.signalAll();
			System.out.println("[PORTER] passengersWaiting: "
					+ passengersWaiting);
			while ((passengersWaiting > 0) || (!conveyourBelt.isEmpty())) {
				cond.await();
			}
			waitingForBags = true;
			currentFlight++;
		} finally {
			lock.unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see IPassengerBaggageCollectionPoint#tryToCollectABag(int)
	 */
	@Override
	public boolean tryToCollectABag(int passengerNumber, int flightNum) throws InterruptedException {
		lock.lock();
		try {
			if (currentFlight != flightNum) {
				return false;
			}

			passengersWaiting++;
			System.out.println("[" + passengerNumber + " tryToCollectBag] trying to collect a bag! \n");
			Iterator<Integer> i;

			while (waitingForBags || (!conveyourBelt.isEmpty())) {
				i = conveyourBelt.iterator();
				while (i.hasNext())
					if (i.next() == passengerNumber) {
						i.remove();
						cond.signalAll();
						passengersWaiting--;
						// System.out.println("passengersWaiting: "+passengersWaiting);
						if (passengersWaiting == 0) {
							cond.signal();
						}
						return true;
					}
				cond.await();
			}

			passengersWaiting--;
			if (passengersWaiting == 0) {
				cond.signal();
			}
			// System.out.println("passengersWaiting: "+passengersWaiting);
			return false;
		} finally {
			lock.unlock();
		}
	}

}
