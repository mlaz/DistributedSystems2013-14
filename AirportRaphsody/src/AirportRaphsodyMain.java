import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;



/**
 * 
 */

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
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
		
		MGeneralRepository genRep = new MGeneralRepository(N, T, "log1.log");
		//genRep.endSimulation();
		//System.exit(0);
		
		MBus bus = new MBus(T, genRep);
		MDepartureTerminalEntrace departureTerminalEntrace = new MDepartureTerminalEntrace(N, genRep);
		MArrivalTerminalExit arrivalTerminalExit = new MArrivalTerminalExit(K, N, T, genRep);
		MBaggagePickupZone baggagePickupZone = new MBaggagePickupZone(genRep);
		MBaggageReclaimGuichet reclaimGuichet = new MBaggageReclaimGuichet(genRep);
		MTempBaggageStorage baggageStorage = new MTempBaggageStorage(genRep);
		MArrivalTerminal arrivalTerminal = new MArrivalTerminal(K, N, M, genRep);	
		
		TPorter porter = new TPorter(genRep);
		TDriver driver = new TDriver(genRep);
		driver.start();
		porter.start();		
		
		TPassenger[][] passengerList =  new TPassenger[K][N];	
		int flightNumber;
		int passNumber;
		int nbags = M;
		Boolean transit = true;
		for(flightNumber = 0; flightNumber < K; flightNumber++){
		//generating passengers 	
			for (passNumber = 0; passNumber < N; passNumber++) {
				passengerList[flightNumber][passNumber] = new TPassenger(passNumber, nbags, transit, flightNumber, genRep);
			
				transit = !transit;
				nbags = (nbags == M) ? 0 : nbags + 1;
			}
			
			System.out.println("flight# " + flightNumber + " NPASSENGERS:" + N);
			//starting threads
			System.out.println("NEW airplane-----------------------------------------\n");
			
			for (passNumber = 0; passNumber < N; passNumber++) 
				passengerList[flightNumber][passNumber].start();
				
				
			for (passNumber = 0; passNumber < N; passNumber++)
				try {
					passengerList[flightNumber][passNumber].join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			System.out.println("ALL Passengers Done-----------------------------------------\n");
			
			
		}
		
		try {
			porter.join();
			driver.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("ALL Threads Done-----------------------------------------\n");
		
		genRep.endSimulation();
		System.exit(0);
	}

}
