package Server.bus;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import Driver.IDriverBus;
import Passenger.IPassengerBus;

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 */
public class MBus implements IDriverBus, IPassengerBus {

	private IBusGenRep genRep;
	private enum Locations {ARR_TERM, DEP_TERM} 
	private Locations location = Locations.ARR_TERM;
	private int nSeats;
	private int occupiedSeats;
	private int[] seats;
	private Timer timer;
	private boolean timerExpired;
	private Lock lock;
	private Condition busFullORTimerExpired;	//driver is parked and waiting for passengers/timer
	private Condition busNotEmpty; 				//driver is parked and waiting for passengers to leave the bus
	private Condition busMoving;				//passengers are waiting to exit the bus
	private Condition busHasNotArrived;			//passengers are waiting for the bus
	
	/**
	 * @param nSeats
	 */
	public MBus(int nSeats,int busDepartureInterval, IBusGenRep genRep) {
		this.nSeats = nSeats;
		occupiedSeats = 0;
		location = Locations.ARR_TERM;
		seats = new int[nSeats];
		int i;
		for(i = 0; i< nSeats; i++)
			seats[i] = -1;
		this.genRep = genRep;
		this.timer = new Timer();
		timer.schedule(new BusTimerExpired(), 0, busDepartureInterval);
		timerExpired = false;
		
		lock = new ReentrantLock();
		busFullORTimerExpired = lock.newCondition();
		busNotEmpty 		  = lock.newCondition();
		busMoving  			  = lock.newCondition();
		busHasNotArrived  	  = lock.newCondition();
	}

	private class BusTimerExpired extends TimerTask {
		@Override
        public void run() {
            if( occupiedSeats > 0 ) {
            	System.out.println("[TIMER] The BUS may leave (occupied seats:" + occupiedSeats + ")!");
            	timerExpired = true;
            	timerExpired();
            }
        }
    }

	private void timerExpired() {
    	lock.lock();
    	try {
    		busFullORTimerExpired.signal();
    	} finally {
    		lock.unlock();
    	}
	}
	
	/* (non-Javadoc)
	 * @see IDriverBus#hasDaysWorkEnded()
	 */
	@Override
	public void waitingForPassengers() throws InterruptedException {
		lock.lock();
		try {
			busHasNotArrived.signalAll();
			location = Locations.ARR_TERM;
			System.out.println("[Driver] occupiedSeats:" + occupiedSeats + "(waiting for more)");

			// while the bus isn't full and the timer hasn't expired
			while (occupiedSeats < nSeats && !timerExpired) {
				System.out.println("[DRIVER] Im waiting with " + occupiedSeats + " passengers on board (timerExpired=" + timerExpired + ")");
				busFullORTimerExpired.await(); // wait for passengers to come in or for timer to expire
			}
			System.out.println("[DRIVER] Departing!");
			timerExpired = false;
		} finally {
			lock.unlock();
		}
	}
	
	/* (non-Javadoc)
	 * @see IDriverBus#parkAndLetPassOff()
	 */
	@Override
	public int parkAndLetPassOff() throws InterruptedException {
		lock.lock();

		try {
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
			return passengers;
		} finally {
			lock.unlock();
		}
	}

	/* (non-Javadoc)
	 * @see IPassengerBus#enterTheBus()
	 */
	@Override
	public boolean enterTheBus(int passNum) throws InterruptedException {
		lock.lock();
		try {	
			if (location != Locations.ARR_TERM) {
				return false;
			}
			
			seats[occupiedSeats] = passNum;
			genRep.updateDriverSeats(seats);
			occupiedSeats++;
			
			System.out.printf("[%d EnterBus] occupiedSeats: %d\n", passNum, occupiedSeats);
			
			if (occupiedSeats == nSeats) {		//if I occupied the last seat
				busFullORTimerExpired.signal();	//	notify the bus driver
			}
			
//			while (location != Locations.DEP_TERM) {
//				System.out.println("["+passNum+"] Im trying to enter the bus");
//				busHasNotArrived.await();
//			}
			return true;
		} finally {
			lock.unlock();
		}
	}

	/* (non-Javadoc)
	 * @see IPassengerBus#leaveTheBus()
	 */
	@Override
	public void leaveTheBus(int passNum) throws InterruptedException {
		lock.lock();
		try {
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
			
			genRep.updateDriverSeats(seats);
			occupiedSeats--;
			
			System.out.println("[" + passNum + " LeaveBus] + occupiedSeats:" + occupiedSeats);
			
			if (occupiedSeats == 0) {
				busNotEmpty.signal();
			}
		} finally {
			lock.unlock();
		}
	}
}
