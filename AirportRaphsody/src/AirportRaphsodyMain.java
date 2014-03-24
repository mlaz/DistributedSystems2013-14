

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
            
		int K = 5;
		int N = 6;
		int M = 2;
		int T = 3;
		
		MGeneralRepository genRep = new MGeneralRepository();
		

		MBus bus = new MBus(T, genRep);
		MArrivalTerminalTransferZone arrivalTerminalTransferZone = new MArrivalTerminalTransferZone(T, genRep);
		MArrivalTerminalExit arrivalTerminalExit = new MArrivalTerminalExit(genRep);
		MBaggagePickupZone baggagePickupZone = new MBaggagePickupZone(genRep);
		MBaggageReclaimGuichet reclaimGuichet = new MBaggageReclaimGuichet(genRep);
		MTempBaggageStorage baggageStorage = new MTempBaggageStorage(genRep);
		MArrivalTerminal arrivalTerminal = new MArrivalTerminal(K, N, M, genRep);	
		
		TPorter porter = new TPorter(genRep);

		
		TDriver driver = new TDriver(genRep);
		driver.start();
		porter.start();		
	}

}
