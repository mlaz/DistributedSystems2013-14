import java.util.LinkedList;
import java.util.Queue;
import java.util.Iterator;
/**
 * 
 */


/**
 * @author Miguel Azevedo, Filipe Teixeira
 * 
 */
public class MArrivalTerminal implements IPassengerArrivalTerminal, IPorterArrivalTerminal {

	private int remainingPassengers = 0;
	private boolean ongoingArrival = false; //ongoing plane arrival
	MGeneralRepository genRep;
	private Queue<MAirplane> airplaneQueue;
	private Queue<TPassenger> passengerQueue = null;
	
	public MArrivalTerminal(int nAirplanes, int nPassengers, int MaxBags, MGeneralRepository genRep) {
		
		this.genRep = genRep;
		genRep.setArrivalTerminal(this);
		
		int i;
		airplaneQueue = new LinkedList<MAirplane>();
		//generating airplanes
		for (i = 0; i < nAirplanes; i++) {
			airplaneQueue.add(new MAirplane(i, nPassengers, MaxBags, genRep));
		}
		
	}
	
	/* (non-Javadoc)
	 * @see IPorterArrivalTerminal#takeARest
	 * ()
	 */
	@Override
	public synchronized MAirplane takeARest() throws InterruptedException {
		if (!ongoingArrival) {
			
			if (airplaneQueue.isEmpty())
				return null;// porter dies

			passengerQueue = airplaneQueue.peek().getPassengers();
			remainingPassengers = passengerQueue.size();
			
			Iterator<TPassenger> iterator = passengerQueue.iterator();
			while(iterator.hasNext())
				iterator.next().start();
		}
		
		while (remainingPassengers > 0)
			wait();
		//System.out.println("no more passengers\n");
		ongoingArrival = false;
		return airplaneQueue.poll();
	}

	/* (non-Javadoc)
	 * @see IPassengerArrivalTerminal#whatSouldIDo()
	 */
	@Override
	public synchronized void whatSouldIDo(int passengerId) throws InterruptedException {
		//System.out.println("What should I do?: "+ passengerId + "\n");
		while (passengerQueue.peek().getPassNumber() != passengerId)
			wait();
		
		passengerQueue.poll();
		remainingPassengers--;
		//System.out.println("passenger Q size:" + passengerQueue.size() + "\n");
		notifyAll();
	}
	
}
