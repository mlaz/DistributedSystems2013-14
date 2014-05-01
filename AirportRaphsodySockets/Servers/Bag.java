package Servers;

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 * Classe que descreve o objecto mala com todos os dados que lhe sao adjacentes
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
