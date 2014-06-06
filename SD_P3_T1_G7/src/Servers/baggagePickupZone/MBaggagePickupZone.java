package Servers.baggagePickupZone;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import Utils.ClockTuple;
import Utils.VectorClock;

/**
 * 
 */

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 * Monitor da zona de recolha de bagagens 
 */
public class MBaggagePickupZone implements IBaggagePickupZone {
	private boolean looseNextItem;
	private int currentFlight;
	private int passengersWaiting;
	private boolean waitingForBags;
	private LinkedList<Integer> conveyourBelt;
	private Lock lock;
	private Condition cond;
	private VectorClock vecClock;
    /**
     *
     */
    public MBaggagePickupZone(int numEntities) {
		conveyourBelt = new LinkedList<Integer>();
		looseNextItem = false;
		passengersWaiting = 0;
		waitingForBags = true;
		currentFlight = 0;
		lock = new ReentrantLock();
		cond = lock.newCondition();
		this.vecClock = new VectorClock(numEntities);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see IPorterBaggagePickupZone#placeBag(Bag)
	 */
	@Override
	public ClockTuple<Boolean> carryItToAppropriateStore(int passId, VectorClock extClk) {
		lock.lock();
		try {
			vecClock.updateClock(extClk);
			looseNextItem = !looseNextItem;
			if (looseNextItem) {
				return new ClockTuple<Boolean>(false, vecClock);
			}

			System.out.println("Bag from passenger: " + passId + " droped at BPZ.\n");
			conveyourBelt.add((Integer) passId);
			// waitingForBags = true;
			cond.signalAll();
			return new ClockTuple<Boolean>(true, vecClock);
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
	public VectorClock noMoreBagsToCollect(VectorClock extClk) throws InterruptedException {
		lock.lock();
		try {
			vecClock.updateClock(extClk);
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
			
			return vecClock;
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
	public ClockTuple<Boolean> tryToCollectABag(int passengerNumber, int flightNum, VectorClock extClk) throws InterruptedException {
		lock.lock();
		try {
			vecClock.updateClock(extClk);
			if (currentFlight != flightNum) {
				return new ClockTuple<Boolean>(false, vecClock);
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
						return new ClockTuple<Boolean>(true, vecClock);
					}
				cond.await();
			}

			passengersWaiting--;
			if (passengersWaiting == 0) {
				cond.signal();
			}
			// System.out.println("passengersWaiting: "+passengersWaiting);
			return new ClockTuple<Boolean>(false, vecClock);
		} finally {
			lock.unlock();
		}
	}

}
