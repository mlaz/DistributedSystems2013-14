package Servers.genRep;

import Porter.EPorterStates;

/**
 * Class that stores the porter information. Used only for logging porposes
 * 
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 */
class PorterInfo {
    /**
     * The state of the porter
     */
    private EPorterStates stat;
    /**
     * The number of bags in the conbeyor belt
     */
    private int convBeltItems;
    /**
     * The number of bags in the temporary baggage storage
     */
    private int storedBaggage;
    /**
     * True if the porter is dead
     */
    private boolean dead;
    
    /**
     * Instanciates a PorterInfo object.
     */
    public PorterInfo() {
        stat = EPorterStates.WAITING_FOR_A_PLANE_TO_LAND;
        dead = false;
    }

    /**
     * Updates the state of the porter
     * @param stat
     */
    public void setStat(EPorterStates stat) {
        this.stat = stat;
    }

    /**
     * Adds a new item to the conveyor belt
     */
    public void addconvBeltItem() {
        this.convBeltItems++;
    }

    /**
     * Adds a new item to the temporary storage
     */
    public void addStoredBaggage() {
        this.storedBaggage++;
    }
    
    /**
     * Set the porter as dead
     */
    public void setAsDead() {
    	dead = true;
    }
    
    /**
     * Returns true if the porter is dead
     * @return True if the porter is dead
     */
    public boolean isDead() {
    	return dead;
    }
    
    @Override
    public String toString() {
    	String stat;
    	 switch (this.stat) {
    	   case WAITING_FOR_A_PLANE_TO_LAND:  
    		   stat = "WTPL";
    		   break;
    	   case AT_THE_PLANES_HOLD:           
    		   stat = "APHL";
    		   break;
    	   case AT_THE_LUGGAGE_BELT_CONVEYOR:
    		   stat = "ALBC";
    		   break;
    	   default:
    		   stat = "ASTR";
    		   break;
    	  }
        return "  " + stat + "  " + convBeltItems + "  " + storedBaggage + " "  ;
    }
}
