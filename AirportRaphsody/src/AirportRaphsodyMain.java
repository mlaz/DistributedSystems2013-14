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
	
		int K = 2;
		int N = 6;
		int M = 2;
		
		MGeneralRepository genRep = new MGeneralRepository();
		

		
		MArrivalTerminalExit arrivalTerminalExit = new MArrivalTerminalExit(genRep);
		MBaggagePickupZone baggagePickupZone = new MBaggagePickupZone(genRep);
		MBaggageReclaimGuichet reclaimGuichet = new MBaggageReclaimGuichet(genRep);
		MTempBaggageStorage baggageStorage = new MTempBaggageStorage();
		MArrivalTerminal arrivalTerminal = new MArrivalTerminal(K, N, M, genRep);	
		
		TPorter porter = new TPorter(arrivalTerminal, baggagePickupZone, baggageStorage);
		porter.start();
		
	}

}
