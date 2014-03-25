import java.util.Stack;
import java.util.LinkedList;

/**
 * 
 */

/**
 * @author miguel
 *
 */
public class MAirplane implements IPorterAirplane {

	private LinkedList<TPassenger> passengerList;
	private Stack<Bag> planesHold;
	//private MGeneralRepository genRep;
        
	public MAirplane (int planeNumber, int nPassengers, int maxBags, MGeneralRepository genRep) {
		//this.genRep = genRep;
		
		passengerList = new LinkedList<TPassenger>();		
		planesHold = new Stack<Bag>();
		
		int i;
		int j;
		int nbags = maxBags;
		//Boolean transit = false;
		Boolean transit = true;//false;
		//generating passengers and bags 
		for (i = 0; i < nPassengers; i++) {
			passengerList.add(new TPassenger(i, nbags, transit, planeNumber, genRep));
			
			for (j = 0 ; j < nbags; j++)
				planesHold.add(new Bag(i,transit));
			
			transit = !transit;
			nbags = (nbags == maxBags) ? 0 : nbags + 1;
        }
		System.out.println("Plane# " + planeNumber + " NBAGS:" + planesHold.size());
	}
	
	public LinkedList<TPassenger> getPassengers () {
		return passengerList;
	}
	
	public synchronized Bag tryToCollectABag () {
		if (planesHold.isEmpty())
			return null;
		return planesHold.pop();
	}
}
