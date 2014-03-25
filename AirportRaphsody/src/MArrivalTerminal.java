import java.util.LinkedList;
import java.util.Queue;
/**
 * 
 */
import java.util.Stack;


/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 * 
 */
public class MArrivalTerminal implements IPassengerArrivalTerminal, IPorterArrivalTerminal {

	private Queue<Stack<Bag>> flightQueue;
	private Stack<Bag> currentPlanesHold;
	private boolean ongoingArrival;
	private int passengersPerPlane;
	private int remainingPassengers;
	private MGeneralRepository genRep;
	
	public MArrivalTerminal(int nFlights, int nPassengers, int maxBags, MGeneralRepository genRep) {
		
		this.genRep = genRep;
		genRep.setArrivalTerminal(this);
		
		//generating bags
		int i;
		int j;
		int k;
		int nbags = maxBags;
		Boolean transit = false;
		Stack<Bag> planesHold;
		flightQueue = new LinkedList <Stack<Bag>>();
		for(k = 0; k < nFlights; k++) {
			planesHold = new Stack<Bag>();
			for (i = 0; i < nPassengers; i++) {
				for (j = 0 ; j < nbags; j++)
					planesHold.add(new Bag(i,transit));
			
				transit = !transit;
				nbags = (nbags == maxBags) ? 0 : nbags + 1;
			}
			System.out.println("Flight# " + k + " NBAGS:" + planesHold.size());
			flightQueue.add(planesHold);
		}
		
		ongoingArrival = true;
		passengersPerPlane = remainingPassengers = nPassengers;
	}
	
	/** 
	 * @see IPorterArrivalTerminal#takeARest
	 * ()
	 */
	@Override
	public synchronized boolean takeARest() throws InterruptedException {
		if (ongoingArrival) {
			while (remainingPassengers > 0)
				wait();
			//System.out.println("no more passengers\n");
			//remainingPlanes--;
			currentPlanesHold = flightQueue.poll();
			remainingPassengers = passengersPerPlane;
		}
		ongoingArrival = !(flightQueue.isEmpty());
		return !currentPlanesHold.isEmpty();
	}

	/**
	 * @see IPassengerArrivalTerminal#whatSouldIDo()
	 */
	@Override
	public synchronized void whatSouldIDo(int passengerId) throws InterruptedException {
		//System.out.println("What should I do?: "+ passengerId + "\n");
		remainingPassengers--;
		if (remainingPassengers == 0) {
			ongoingArrival = false;
			notify();
		}
	}
	

	public synchronized Bag tryToCollectABag () {
		if (currentPlanesHold.isEmpty())
			return null;
		return currentPlanesHold.pop();
	}

	
}
