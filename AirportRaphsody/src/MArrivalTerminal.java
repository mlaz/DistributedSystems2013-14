import java.util.Queue;

/**
 * 
 */

/**
 * @author Miguel Azevedo, Filipe Teixeira
 * 
 */
public class MArrivalTerminal implements IPassangerArrivalTerminal, IPorterArrivalTerminal {

	private int remainingPassengers = 0;
	private Queue<MAirplane> airplaneQueue; 
	
	public MArrivalTerminal (Queue<MAirplane> airplaneQueue) {
		this.airplaneQueue = airplaneQueue;
	}
	
	/* (non-Javadoc)
	 * @see IPorterArrivalTerminal#takeARest
	 * ()
	 */
	@Override
	public synchronized MAirplane takeARest() throws InterruptedException {
		while (remainingPassengers > 0)
			wait();
		
		return airplaneQueue.poll();
	}
	
}
