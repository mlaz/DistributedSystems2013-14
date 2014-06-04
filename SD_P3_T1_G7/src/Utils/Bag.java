package Utils;
/**
 * 
 */

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 * Classe Bag representa uma mala e toda a informaçao que lhe é adjacente
 */
public class Bag {
	
	private int passNumber;
	private Boolean inTransit;

    /**
     *
     * @param passNumber
     * @param inTransit
     */
    public Bag(int passNumber, Boolean inTransit) {
		this.passNumber = passNumber;
		this.inTransit = inTransit;
	}

    /**
     *
     * @return
     */
    public int getPassNumber () {
		return passNumber;
	}

    /**
     *
     * @return
     */
    public Boolean isInTransit () {
		return inTransit;
	}
}
