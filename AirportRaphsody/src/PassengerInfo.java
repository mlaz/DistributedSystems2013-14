/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 */
class PassengerInfo {
    
    private int id;
    private boolean inTransit;
    private int startingLuggage;
    
    private TPassenger.states stat;
    private int currentLuggage;

    public PassengerInfo(int id, boolean inTransit, int startingLuggage) {
        this.id = id;
        this.inTransit = inTransit;
        this.startingLuggage = startingLuggage;
        this.currentLuggage = 0;
        this.stat = TPassenger.states.EXITING_THE_ARRIVAL_TERMINAL;
    }

    public int getId() { 
    	return id;
    }
    
    public void setStat(TPassenger.states stat) {
        this.stat = stat;
    }

    public int getCurrentLuggage() {
        return currentLuggage;
    }

    public void gotLuggage() {
        this.currentLuggage++;
    }

    public int getStartingLuggage() {
        return startingLuggage;
    }

    @Override
    public String toString() {
    	String situation = (inTransit) ? "TRT" : "FDT";
    	String stat;
    	switch (this.stat) {
    	 case AT_THE_DISEMBARKING_ZONE:           
    		stat= "ADZ";
         	break;
    
    	 case AT_THE_LUGGAGE_COLLECTION_POINT:   
    		 stat = "LCP";
    		 break;
    	 case AT_THE_BAGGAGE_RECLAIM_OFFICE:      
    		 stat = "BRO";
         	 break;
    	 case EXITING_THE_ARRIVAL_TERMINAL:       
    		 stat = "EAT";
    	 	 break;
    	 case AT_THE_ARRIVAL_TRANSFER_TERMINAL:   
    		 stat = "ATT";
         	 break;
    	 case TERMINAL_TRANSFER:                  
    		 stat = "TTF";
         	 break;
    	 case AT_THE_DEPARTURE_TRANSFER_TERMINAL: 
    		 stat = "DTT";
         	 break;
    	 default:    
    		 stat = "EDT";
         	 break;
    	}
        return stat + " " + situation + "  " + startingLuggage + "   " + currentLuggage + "  ";
    }
}
