/**
 * 
 */

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 * 
 */
public class MDepartureTerminalEntrace {
	private int remainingPassengers;
	private int N;
	public MDepartureTerminalEntrace (int N, MGeneralRepository genRep) {
		remainingPassengers = caclNumPassengers(N);
		genRep.setDepartureTerminalEntrace(this);
		this.N = N;
	}
	
	/**
	 * This uses the same algorithm used to generate the passenger threads
	 * @param number of passengers per plane
	 * @return number of expected 
	 */
	private int caclNumPassengers(int N) {
		int n = 0;
		int passNumber;
		Boolean transit = true;
		for (passNumber = 0; passNumber < N; passNumber++) {
			transit = !transit;
			if (transit)
				n++;
		}
		return n;
	}
	
	public synchronized void prepareNextLeg() throws InterruptedException {
		remainingPassengers--;
		
		if (remainingPassengers > 0)
			wait();
		else {
			remainingPassengers = caclNumPassengers(N);
			notifyAll();
		}
	}

}
