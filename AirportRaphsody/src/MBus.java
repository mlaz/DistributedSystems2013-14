/**
 * 
 */

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 *
 */
public class MBus implements IDriverBus, IPassengerBus {

	private MGeneralRepository genRep;
	private enum Locations {ARR_TERM, DEP_TERM} 
	private Locations location = Locations.ARR_TERM;
	private int nSeats;
	private int occupiedSeats;
	private int[] seats;
	
	/**
	 * @param nSeats
	 */
	public MBus(int nSeats, MGeneralRepository genRep) {
		genRep.setBus(this);
		this.nSeats = nSeats;
		occupiedSeats = 0;
		location = Locations.ARR_TERM;
		seats = new int[nSeats];
		int i;
		for(i = 0; i< nSeats; i++)
			seats[i] = -1;
		this.genRep = genRep;
	}

	/* (non-Javadoc)
	 * @see IDriverBus#hasDaysWorkEnded()
	 */
	@Override
	public synchronized void waitingForPassengers() throws InterruptedException {
		notifyAll();
		location = Locations.ARR_TERM;
		System.out.println("Driver : occupiedSeats:" + occupiedSeats);
		while (occupiedSeats == 0)
                    wait(1000);
	}
	
	/* (non-Javadoc)
	 * @see IDriverBus#parkAndLetPassOff()
	 */
	@Override
	public synchronized int parkAndLetPassOff() throws InterruptedException {
		
		int passengers = occupiedSeats;
		location = Locations.DEP_TERM;
		notifyAll();
		
		while (occupiedSeats > 0)
			wait();
		location = Locations.ARR_TERM;
		System.out.println("BUSPASSENGERS:" + passengers);
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
		System.out.println("occupiedSeats:" + occupiedSeats);
		if (occupiedSeats == nSeats)
			notifyAll();
		
		while (location != Locations.DEP_TERM)
			wait();
		return true;
	}

	/* (non-Javadoc)
	 * @see IPassengerBus#leaveTheBus()
	 */
	@Override
	public synchronized void leaveTheBus(int passNum) throws InterruptedException {
		
		while (location != Locations.DEP_TERM)
			wait();
		
		int i;
		for (i = 0; i < nSeats; i++)
			if (seats[i] == passNum)
				seats[i] = -1;
		genRep.updateDriverSeats(seats);
		occupiedSeats--;
		System.out.println("occupiedSeats:" + occupiedSeats);
		if (occupiedSeats == 0)
			notify();
	}

	
}
