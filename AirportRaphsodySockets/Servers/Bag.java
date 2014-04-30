package Servers;

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 *
 */
public class Bag {
	
	private int passNumber;
	private Boolean inTransit;
	
	public Bag(int passNumber, Boolean inTransit) {
		this.passNumber = passNumber;
		this.inTransit = inTransit;
	}
	
	public int getPassNumber () {
		return passNumber;
	}
	
	public Boolean isInTransit () {
		return inTransit;
	}
}
