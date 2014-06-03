package Servers.arrivalTerminalExit;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import Servers.clientsInterfaces.*;
/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 * Monitor da saida do terminal de chegada
 */
public class MArrivalTerminalExit implements IDriverArrivalTerminalTransferZone, IPassengerArrivalExitTransferZone {

	private int totalPassengers;
	private int passengersDone = 0;
	private int nSeats;
	private int passengersToGo;				// number of empty seats available on the bus
	private Queue<Integer> busQueue;
	private boolean availableBus = false;
	private IArrivalTerminalExitGenRep genRep;
	private Lock lock;
	private Condition noPassengersInQueue;
	private Condition busReady;
	
	/**
     * @param nAirplanes
	 * @param genRep
     * @param nPassengers
     * @param nSeats
	 */
	public MArrivalTerminalExit(int nAirplanes, int nPassengers, int nSeats, IArrivalTerminalExitGenRep genRep) {
		this.nSeats = nSeats;
		busQueue = new LinkedList<Integer>();
		totalPassengers = nAirplanes * nPassengers;
		this.genRep = genRep;
		
		lock = new ReentrantLock();
		noPassengersInQueue = lock.newCondition();
		busReady			= lock.newCondition();				
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see IPassengerArrivalTerminalTransferZone#takeABus()
	 */
	@Override
	public void takeABus(int passNumber) throws InterruptedException {
		lock.lock();
		try {
			busQueue.add((Integer) passNumber);
			noPassengersInQueue.signal();	//notify the driver that Im waiting in line!
//			if (busQueue.size() == nSeats) {
//				notifyAll(); // the line is enough to fill the bus > why notifyAll? > who am i notifying?
//				noPassengersInQueue.signalAll();
//			}
			
			genRep.updateDriverQueue(toIntArray(busQueue.toArray()));
	
			System.out.println("[" + passNumber + " TakeBus] Passengers waiting to enter the bus: " + busQueue.size());
			
			//     (   its not my turn to enter  ) OR (the bus isn't here) 
			while ((passNumber != busQueue.peek()) || (!availableBus)) {
				busReady.await();
			}
	
			passengersToGo--;
			busQueue.poll();
			
			genRep.updateDriverQueue(toIntArray(busQueue.toArray()));
	
			if (passengersToGo == 0) {
				availableBus = false;
			}
			
			busReady.signalAll();; //notify who? about what? > notify other passengers that they may enter try to enter the bus
			
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
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see IDriverArrivalTerminalTransferZone#announcingBusBoaring()
	 */
	@Override
	public boolean announcingBusBoaring(int lastPassengers) throws InterruptedException {
		lock.lock();
		try {
			passengersDone = passengersDone + lastPassengers;
			System.out.println("[DRIVER] passengersDone:" + passengersDone);
			if (totalPassengers == passengersDone) {
				return false;  	/* the simulation is over there are no more passengers to process */
			}
	
			passengersToGo = nSeats;
			availableBus = true;
	
			if (busQueue.isEmpty()) { // no one on line to enter the bus
				System.out.println("[DRIVER] No one's waiting. I'll wait");
				noPassengersInQueue.await(); // wait for passengers to arrive
				System.out.println("[DRIVER] Still no one?");
				if (totalPassengers == passengersDone)
					return false; // the simulation is over there are no more passengers to process
			} else { // someone to go
				int size;
				passengersToGo = ((size = busQueue.size()) < nSeats) ? size : nSeats;
				busReady.signalAll();	//notify all passengers that they can come in
			}
			
			System.out.println("DRIVER ANNOUNCED -> You can come in!");
			return true;
			
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 
     * @param passengerNumber
	 */
	public void goHome(int passengerNumber) {
		lock.lock();
		try {
			passengersDone++;
			System.out.println("[" + passengerNumber + " goHome] passengersDone:" + passengersDone);
			
			if (totalPassengers == passengersDone) {
				noPassengersInQueue.signal(); 	// so the driver knows there is no one waiting
			}
			
		} finally {
			lock.unlock();
		}
	}

    /**
     *
     */
    @Override
	public void announcingDeparture() {
		lock.lock();
		try {
			System.out.println("[DRIVER] Im leaving!");
			availableBus = false;
		} finally {
			lock.unlock();
		}
	}

}
