package Utils;

import java.io.Serializable;

/**
 * Classe Bag representa uma mala e toda a informaçao que lhe é adjacente.
 * 
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 */
public class Bag implements Serializable {
	
	private static final long serialVersionUID = -7727878935876516744L;
	
	/**
	 * The identification of the passenger.
	 */
	private int passNumber;
	
	/**
	 * Defines if the Bag is in transit or not.
	 */
	private Boolean inTransit;

    /**
     * Instanciates a Bag object.
     * @param passNumber The identification of the passenger.
     * @param inTransit The bag is in transit or not.
     */
    public Bag(int passNumber, Boolean inTransit) {
		this.passNumber = passNumber;
		this.inTransit = inTransit;
	}

    /**
     * Returns the number of the passenger who owns this bag.
     * @return The identification of the passenger
     */
    public int getPassNumber () {
		return passNumber;
	}

    /**
     * Returns true if the bag is in transit, false otherwise.
     * @return True if the bag is in transit, false otherwise.
     */
    public Boolean isInTransit () {
		return inTransit;
	}
}
