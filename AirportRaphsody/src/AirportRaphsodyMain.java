import java.util.Iterator;
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
            
		int K = 5; //number of airplanes
		int N = 6; //number of passengers per airplane
		int M = 2; //maximum number of bags
		int T = 3; //number of bus seat
		
		MGeneralRepository genRep = new MGeneralRepository();
		

		MBus bus = new MBus(T, genRep);
		MArrivalTerminalExit arrivalTerminalExit = new MArrivalTerminalExit(K, N, T, genRep);
		MBaggagePickupZone baggagePickupZone = new MBaggagePickupZone(genRep);
		MBaggageReclaimGuichet reclaimGuichet = new MBaggageReclaimGuichet(genRep);
		MTempBaggageStorage baggageStorage = new MTempBaggageStorage(genRep);
		MArrivalTerminal arrivalTerminal = new MArrivalTerminal(K, N, genRep);	
		
		TPorter porter = new TPorter(genRep);
		TDriver driver = new TDriver(genRep);
		driver.start();
		porter.start();		
		
		MAirplane a;
		LinkedList<MAirplane> airplaneList = new LinkedList<MAirplane>();
		int i;
		for (i = 0; i < K; i++) {
			a = new MAirplane(i, N, M, genRep);
			airplaneList.add(a);
			genRep.addAirplane(a);
		}
		
		LinkedList<TPassenger> passengerList;
		Iterator<TPassenger> passIterator;
		
		Iterator<MAirplane> planeIterator = airplaneList.iterator();
		while (planeIterator.hasNext()) {
			passengerList = planeIterator.next().getPassengers();
			System.out.println("NEW airplane-----------------------------------------\n");
			passIterator = passengerList.iterator();
			while (passIterator.hasNext()) {
				passIterator.next().start();
			}
			
			passIterator = passengerList.iterator();
			while (passIterator.hasNext())
				try {
					passIterator.next().join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			System.out.println("ALL Passengers Done-----------------------------------------\n");
		}
	}

}
