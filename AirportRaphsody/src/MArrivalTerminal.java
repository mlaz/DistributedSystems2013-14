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

	private boolean ongoingArrival;
	private int passengersPerPlane;
	private int remainingPlanes;
	private int remainingPassengers;
	private MGeneralRepository genRep;
	
	public MArrivalTerminal(int nAirplanes, int nPassengers, MGeneralRepository genRep) {
		
		this.genRep = genRep;
		genRep.setArrivalTerminal(this);
		
		ongoingArrival = true;
		remainingPlanes = nAirplanes;
		passengersPerPlane = remainingPassengers = nPassengers;
	}
	
	/** 
	 * @see IPorterArrivalTerminal#takeARest
	 * ()
	 */
	@Override
	public synchronized MAirplane takeARest() throws InterruptedException {
		if (ongoingArrival) {
			while (remainingPassengers > 0)
				wait();
			//System.out.println("no more passengers\n");
			remainingPlanes--;
			remainingPassengers = passengersPerPlane;
		}
		ongoingArrival = (remainingPlanes > 0);
		return genRep.getNextAirPlane();
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
	
}
