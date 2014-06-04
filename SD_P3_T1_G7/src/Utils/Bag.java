package Utils;

import java.io.Serializable;
/**
 * 
 */

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 * Classe Bag representa uma mala e toda a informaçao que lhe é adjacente
 */
public class Bag implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7727878935876516744L;
	
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
