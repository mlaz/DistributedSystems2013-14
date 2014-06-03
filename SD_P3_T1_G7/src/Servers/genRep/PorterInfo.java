package Servers.genRep;

import Servers.EPorterStates;

//import TPorter.states;
/**
 * Classe para logging de informação da thread do bagageiro
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 */
class PorterInfo {
    private EPorterStates stat;
    private int convBeltItems;
    private int storedBaggage;
    private boolean dead;
    
    public PorterInfo(/*String state, int cb, int sr*/) {
        stat = EPorterStates.WAITING_FOR_A_PLANE_TO_LAND;
        //this.cb   = cb;
        //this.sr   = sr;
        dead = false;
    }

    public void setStat(EPorterStates stat) {
        this.stat = stat;
    }

    public void addconvBeltItem() {
        this.convBeltItems++;
    }

    public void addStoredBaggage() {
        this.storedBaggage++;
    }
    
    public void setAsDead() {
    	dead = true;
    }
    
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
