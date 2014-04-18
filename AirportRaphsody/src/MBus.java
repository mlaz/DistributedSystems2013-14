import java.util.Timer;
import java.util.TimerTask;

/**
 * 
 */

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 */
public class MBus implements IDriverBus, IPassengerBus {

	private MGeneralRepository genRep;
	private enum Locations {ARR_TERM, DEP_TERM} 
	private Locations location = Locations.ARR_TERM;
	private int nSeats;
	private int occupiedSeats;
	private int[] seats;
	private Timer timer;
	private boolean timerExpired;
	/**
	 * @param nSeats
	 */
	public MBus(int nSeats,int busDepartureInterval, MGeneralRepository genRep) {
		genRep.setBus(this);
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
	}
	
	private class BusTimerExpired extends TimerTask {
		@Override
        public void run() {
            if( occupiedSeats > 0 ) {
            	System.out.println("[TIMER] The BUS may leave (oSeats:" + occupiedSeats + ")!");
            	timerExpired = true;
            	timerExpired();
            }
        }
    }

	public synchronized void timerExpired() {
    	notifyAll();	//the driver may leave
	}
	
	/* (non-Javadoc)
	 * @see IDriverBus#hasDaysWorkEnded()
	 */
	@Override
	public synchronized void waitingForPassengers() throws InterruptedException {
		notifyAll();
		location = Locations.ARR_TERM;
		System.out.println("[Driver] occupiedSeats:" + occupiedSeats + "(waiting for more)");
		
		//while the bus isn't full and the timer hasn't expired
		while (occupiedSeats < nSeats && !timerExpired) {
			System.out.println("[DRIVER] Im waiting with "+occupiedSeats+" passengers on board (timerExpired="+timerExpired+")");
			wait();	//wait for passengers to come in
		}
		System.out.println("[DRIVER] Departing!");
		timerExpired = false;
	}
	
	/* (non-Javadoc)
	 * @see IDriverBus#parkAndLetPassOff()
	 */
	@Override
	public synchronized int parkAndLetPassOff() throws InterruptedException {
		
		int passengers = occupiedSeats;
		location = Locations.DEP_TERM;
		notifyAll();
		
		while (occupiedSeats > 0) {
			System.out.println("[DRIVR] Waiting for passengers to exit the bus...");
			wait();	//waiting for passengers to exit the bus
		}
		
		location = Locations.ARR_TERM;
		System.out.println("BUSPASSENGERS:" + passengers + "(left the bus)");
		return passengers;
	}

	/* (non-Javadoc)
	 * @see IPassengerBus#enterTheBus()
	 */
	@Override
	public synchronized boolean enterTheBus(int passNum) throws InterruptedException {
		if (location != Locations.ARR_TERM)
			return false;
		
		seats[occupiedSeats] = passNum;
		genRep.updateDriverSeats(seats);
		occupiedSeats++;
		System.out.println("[" + passNum + " EnterBus] occupiedSeats:" + occupiedSeats);
		if (occupiedSeats == nSeats) //if I occupied the last seat
			notify();	//notify the bus driver
		
		while (location != Locations.DEP_TERM) {
			System.out.println("["+passNum+"] Im trying to enter the bus");
			wait();
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see IPassengerBus#leaveTheBus()
	 */
	@Override
	public synchronized void leaveTheBus(int passNum) throws InterruptedException {
		
		while (location != Locations.DEP_TERM) {
			System.out.printf("[%d] Im trying to leave the bus!",passNum);
			wait();
		}
		
		int i;
		for (i = 0; i < nSeats; i++)
			if (seats[i] == passNum)
				seats[i] = -1;
		genRep.updateDriverSeats(seats);
		occupiedSeats--;
		System.out.println("[" + passNum + " LeaveBus] + occupiedSeats:" + occupiedSeats);
		if (occupiedSeats == 0)
			notify();
	}
}
