/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 *
 */
public class AirportRaphsodyMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if( args.length < 5) {
			System.err.println("Wrong number of arguments.");
			System.err.println("Usage: java AirportRaphsodyMain [numAirplanes] [numPassengersPerPlane] [maxBags] [numberBusSeats] [busTimer]");
			System.exit(1);
		}
		
		int K = Integer.parseInt(args[0]); 	//number of airplanes
		int N = Integer.parseInt(args[1]); 	//number of passengers per airplane
		int M = Integer.parseInt(args[2]); 	//maximum number of bags
		int T = Integer.parseInt(args[3]); 	//number of bus seats
		int busTimer = Integer.parseInt(args[4]); //time between bus travel
		
		MGeneralRepository genRep = new MGeneralRepository(N, T, "log1.log");
		//genRep.endSimulation();
		//System.exit(0);
		
		new MBus(T, busTimer, genRep);
		new MDepartureTerminalEntrace(N, genRep);
		new MArrivalTerminalExit(K, N, T, genRep);
		new MBaggagePickupZone(genRep);
		new MBaggageReclaimGuichet(genRep);
		new MTempBaggageStorage(genRep);
		new MArrivalTerminal(K, N, M, genRep);	
		
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
