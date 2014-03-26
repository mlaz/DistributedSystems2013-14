
//import TPorter.states;
/**
 *
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 */
class PorterInfo {
    private TPorter.states stat;
    private int collectedBaggaeg;
    private int storedBaggage;

    public PorterInfo(/*String state, int cb, int sr*/) {
        stat = TPorter.states.WAITING_FOR_A_PLANE_TO_LAND;
        //this.cb   = cb;
        //this.sr   = sr;
    }

    public void setStat(TPorter.states stat) {
        this.stat = stat;
    }

    public void setCb(int cb) {
        this.collectedBaggaeg = cb;
    }

    public void setSr(int sr) {
        this.storedBaggage = sr;
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
        return "  " + stat + "  " + collectedBaggaeg + "  " + storedBaggage + " "  ;
    }
}
