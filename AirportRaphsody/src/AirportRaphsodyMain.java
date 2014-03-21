import java.util.Queue;
import java.util.Stack;
import java.util.LinkedList;

/**
 * 
 */

/**
 * @author miguel
 *
 */
public class AirportRaphsodyMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Stack<Bag> planesHold = new Stack<Bag>();
		int i = 0;
		Boolean transit = false;
		
		for (i = 0; i < 12; i++) {
			planesHold.add(new Bag(i,transit));
			transit = !transit;
		}
		
		Queue<MAirplane> airplaneQueue = new LinkedList<MAirplane>();
		airplaneQueue.add (new MAirplane(planesHold));
		
		MArrivalTerminal arrivalTerminal = new MArrivalTerminal(airplaneQueue);
		MBaggagePickupZone baggagePickupZone = new MBaggagePickupZone();
		MTempBaggageStorage baggageStorage = new MTempBaggageStorage();
		TPorter porter = new TPorter(arrivalTerminal, baggagePickupZone, baggageStorage);
		
		porter.run();
		
	}

}
