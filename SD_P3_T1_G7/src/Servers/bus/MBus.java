package Servers.bus;
import java.rmi.RemoteException;
import java.util.concurrent.TimeUnit;
//import java.util.Timer;
//import java.util.TimerTask;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import Utils.ClockTuple;
import Utils.VectorClock;


/**
 * Class that implements the Bus server services.
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 */
public class MBus implements IBus {

	/**
	 * Interface for communications with the General Repository
	 */
	private IBusGenRep genRep;
	/**
	 * Enum data type with the possible locations of the bus
	 */
	private enum Locations {ARR_TERM, DEP_TERM} 
	/**
	 * The current bus location
	 */
	private Locations location = Locations.ARR_TERM;
	/**
	 * The number of seats on the bus
	 */
	private int nSeats;
	/**
	 * The number of currently occupied seats on the bus 
	 */
	private int occupiedSeats;
	/**
	 * The seats of bus.
	 */
	private int[] seats;
	/**
	 * The lock
	 */
	private Lock lock;
	/**
	 * Condition used to wait for passengers or for the time to leave 
	 */
	private Condition busFull;					//driver is parked and waiting for passengers/timer
	/**
	 * Condition used to wait for passengers to leave the bus 
	 */
	private Condition busNotEmpty; 				//driver is parked and waiting for passengers to leave the bus
	/**
	 * Condition used to wait for the bus to stop
	 */
	private Condition busMoving;				//passengers are waiting to exit the bus
	/**
	 * Condition used to wait for the bus arrival
	 */
	private Condition busHasNotArrived;			//passengers are waiting for the bus
	/**
	 * The time that the bus waits for more passengers before leaving
	 */
	private long busInterval;
	/**
	 * The Clock
	 */
	private VectorClock vecClock;
	
	
	/**
	 * Instanciates a Bus.
	 * @param nSeats Te number of seats on the bus.
	 * @param busDepartureInterval The time that the bus waits for more passengers before leaving
	 * @param genRep The General Repository
	 * @param numEntities Te number of entities that will use the VectorClock at the same time
	 */
	public MBus(int nSeats,int busDepartureInterval, IBusGenRep genRep, int numEntities) {
		this.nSeats = nSeats;
		occupiedSeats = 0;
		location = Locations.ARR_TERM;
		seats = new int[nSeats];
		int i;
		for(i = 0; i< nSeats; i++)
			seats[i] = -1;
		this.genRep = genRep;
		this.busInterval = busDepartureInterval;
		this.vecClock = new VectorClock(numEntities);
		lock = new ReentrantLock();
		busFull = lock.newCondition();
		busNotEmpty 		  = lock.newCondition();
		busMoving  			  = lock.newCondition();
		busHasNotArrived  	  = lock.newCondition();
	}
	
	@Override
	public VectorClock waitingForPassengers(VectorClock extClk) throws InterruptedException {
		lock.lock();
		try {
			vecClock.updateClock(extClk);
			busHasNotArrived.signalAll();
			location = Locations.ARR_TERM;
			System.out.println("[Driver] occupiedSeats:" + occupiedSeats + "(waiting for more)");

			do {
				System.out.println("[DRIVER] Im waiting with " + occupiedSeats + " passengers on board ");
				busFull.await(this.busInterval, TimeUnit.MILLISECONDS); // wait for passengers to come in or for timer to expire
			} while (occupiedSeats == 0) ;
			
			return vecClock;
		} finally {
			lock.unlock();
		}
	}
	
	@Override
	public ClockTuple<Integer> parkAndLetPassOff(VectorClock extClk) throws InterruptedException {
		lock.lock();

		try {
			vecClock.updateClock(extClk);
			System.out.println("[DRIVER] IM PARKING!");
			int passengers = occupiedSeats;
			location = Locations.DEP_TERM;
			busMoving.signalAll(); // notify all the passengers that they may leave the bus

			while (occupiedSeats > 0) {
				System.out.println("[DRIVER] Waiting for passengers to exit the bus...");
				busNotEmpty.await(); // waiting for passengers to exit the bus
			}

			location = Locations.ARR_TERM;
			System.out.println("BUSPASSENGERS:" + passengers + "(left the bus)");
			return new ClockTuple<Integer>(passengers, vecClock);
		} finally {
			lock.unlock();
		}
	}

	@Override
	public ClockTuple<Boolean> enterTheBus(int passNum, VectorClock extClk) throws InterruptedException {
		lock.lock();
		try {	
			vecClock.updateClock(extClk);
			if (location != Locations.ARR_TERM || occupiedSeats == nSeats) {
				return new ClockTuple<Boolean>(false, vecClock);
			}
			
			seats[occupiedSeats] = passNum;
			try {
				genRep.updateDriverSeats(seats, vecClock);
			} catch (RemoteException e) {
				e.printStackTrace();
				System.err.println("Unable to UPDATE_DRIVER_SEATS");
			}
			occupiedSeats++;
			
			System.out.printf("[%d EnterBus] occupiedSeats: %d\n", passNum, occupiedSeats);
			
			if (occupiedSeats == nSeats) {		//if I occupied the last seat
				busFull.signal();	//	notify the bus driver
			}
			
			return new ClockTuple<Boolean>(true, vecClock);
		} finally {
			lock.unlock();
		}
	}
	
	@Override
	public VectorClock leaveTheBus(int passNum, VectorClock extClk) throws InterruptedException {
		lock.lock();
		try {
			vecClock.updateClock(extClk);
			while (location != Locations.DEP_TERM) {
				System.out.printf("[%d] The bus is at %s!\n",passNum,location.name());
				busMoving.await();
				System.out.printf("[%d] Waiting to arrive...\n",passNum);
			}
			
			for (int i = 0; i < nSeats; i++) {
				if (seats[i] == passNum) {
					seats[i] = -1;
				}
			}
			
			try {
				genRep.updateDriverSeats(seats, vecClock);
			} catch (RemoteException e) {
				e.printStackTrace();
				System.err.println("Unable to UPDATE_DRIVER_SEATS");
			}
			occupiedSeats--;
			
			System.out.println("[" + passNum + " LeaveBus] + occupiedSeats:" + occupiedSeats);
			
			if (occupiedSeats == 0) {
				busNotEmpty.signal();
			}
			
			return vecClock;
		} finally {
			lock.unlock();
		}
	}
}
