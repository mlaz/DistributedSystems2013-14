package Servers.arrivalTerminalExit;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import Utils.ClockTuple;
import Utils.VectorClock;

/**
 * Class that implements the Arrival Terminal Exit server services.
 * 
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 */
public class MArrivalTerminalExit implements IArrivalTerminalExit {

	/**
	 * The total number of passengers in the simulation
	 */
	private int totalPassengers;
	/**
	 * The number of passengers already processed
	 */
	private int passengersDone = 0;
	/**
	 * The number of seats in the bus
	 */
	private int nSeats;
	/**
	 * The number of currently empty seats on the bus
	 */
	private int passengersToGo;				// number of empty seats available on the bus
	/**
	 * The queue of passengers waiting for the bus
	 */
	private Queue<Integer> busQueue;
	/**
	 * True if the bus is available to board
	 */
	private boolean availableBus = false;
	/**
	 * The interface with the General Repository
	 */
	private IArrivalTerminalExitGenRep genRep;
	/**
	 * The lock
	 */
	private Lock lock;
	/**
	 * Condition to wait for passengers to arrive at the queue 
	 */
	private Condition noPassengersInQueue;
	/**
	 * Condition to wait for the bus to be ready to board
	 */
	private Condition busReady;
	/**
	 * The clock
	 */
	private VectorClock vecClock;
	
	/**
	 * Instanciates a Arrival Terminal Exit object.
	 * 
	 * @param nAirplanes The number of flights of the simulation
	 * @param nPassengers The number of passengers in each flight
	 * @param nSeats The number of seats on the bus
	 * @param genRep The interface with the General Repository
	 */
	public MArrivalTerminalExit(int nAirplanes, int nPassengers, int nSeats, IArrivalTerminalExitGenRep genRep) {
		this.nSeats = nSeats;
		busQueue = new LinkedList<Integer>();
		totalPassengers = nAirplanes * nPassengers;
		this.genRep = genRep;
		this.vecClock = new VectorClock(nPassengers + 2);
		
		lock = new ReentrantLock();
		noPassengersInQueue = lock.newCondition();
		busReady			= lock.newCondition();				
	}

	@Override
	public VectorClock takeABus(int passNumber, VectorClock extClk) throws InterruptedException {
		lock.lock();
		try {
			vecClock.updateClock(extClk);
			busQueue.add((Integer) passNumber);
			noPassengersInQueue.signal();	//notify the driver that Im waiting in line!

			//logging the queue
			try {
				genRep.updateDriverQueue(toIntArray(busQueue.toArray()), vecClock);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
			System.out.println("[" + passNumber + " TakeBus] Passengers waiting to enter the bus: " + busQueue.size());
			
			//     (   its not my turn to enter  ) OR (the bus isn't here) 
			while ((passNumber != busQueue.peek()) || (!availableBus)) {
				busReady.await();
			}
	
			passengersToGo--;
			busQueue.poll();
			
			try {
				genRep.updateDriverQueue(toIntArray(busQueue.toArray()), vecClock);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
			if (passengersToGo == 0) {
				availableBus = false;
			}
			
			busReady.signalAll();
			return vecClock;
		} finally {
			lock.unlock();
		}
	}

	private int[] toIntArray(Object[] array) {
		int[] ints = new int[array.length];
		for( int i=0 ; i<array.length ; i++ ) {
			ints[i] = (Integer)array[i];
		}
		return ints;
	}
	
	@Override
	public ClockTuple<Boolean> announcingBusBoaring(int lastPassengers, VectorClock extClk) throws InterruptedException {
		lock.lock();
		try {
			vecClock.updateClock(extClk);
			passengersDone = passengersDone + lastPassengers;
			System.out.println("[DRIVER] passengersDone:" + passengersDone);
			if (totalPassengers == passengersDone) {
				return new ClockTuple<Boolean>(false, vecClock);  	/* the simulation is over there are no more passengers to process */
			}
	
			passengersToGo = nSeats;
			availableBus = true;
	
			if (busQueue.isEmpty()) { // no one on line to enter the bus
				System.out.println("[DRIVER] No one's waiting. I'll wait");
				noPassengersInQueue.await(); // wait for passengers to arrive
				System.out.println("[DRIVER] Still no one?");
				if (totalPassengers == passengersDone)
					return new ClockTuple<Boolean>(false, vecClock); // the simulation is over there are no more passengers to process
			} else { // someone to go
				int size;
				passengersToGo = ((size = busQueue.size()) < nSeats) ? size : nSeats;
				busReady.signalAll();	//notify all passengers that they can come in
			}
			
			System.out.println("DRIVER ANNOUNCED -> You can come in!");
			return new ClockTuple<Boolean>(true, vecClock);
			
		} finally {
			lock.unlock();
		}
	}

	@Override
	public VectorClock goHome(int passengerNumber, VectorClock extClk) {
		lock.lock();
		try {
			vecClock.updateClock(extClk);
			passengersDone++;
			System.out.println("[" + passengerNumber + " goHome] passengersDone:" + passengersDone);
			
			if (totalPassengers == passengersDone) {
				noPassengersInQueue.signal(); 	// so the driver knows there is no one waiting
			}
			return vecClock;
		} finally {
			lock.unlock();
		}
	}

    @Override
	public VectorClock announcingDeparture(VectorClock extClk) {
		lock.lock();
		try {
			vecClock.updateClock(extClk);
			System.out.println("[DRIVER] Im leaving!");
			availableBus = false;
			return vecClock;
		} finally {
			lock.unlock();
		}
	}

}
