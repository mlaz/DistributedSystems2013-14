/**
 * 
 */

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 * 
 */
public class MBaggageReclaimGuichet {

	/**
	 * @param genRep
	 */
	public MBaggageReclaimGuichet(MGeneralRepository genRep) {
		genRep.setBaggageReclaimGuichet(this);
	}

	/**
	 * 
	 */
	public synchronized void reclaimBags(int passengerNumber) {
		// TODO Auto-generated method stub
		System.out.println("Passenger #"+ passengerNumber + "reclaiming Bags.\n");
	}
}
