package Servers.genRep;

import Passenger.EPassengerStates;

/**
 * Class that stores the driver information. Used only for logging porposes
 * 
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 */
class PassengerInfo {
    
    /**
     * The id of the passenger
     */
    private int id;
    /**
     * True if the passenger is in transit
     */
    private boolean inTransit;
    /**
     * The number of bags that the passenger initially has.
     */
    private int startingLuggage;
    /**
     * The state of the passenger
     */
    private EPassengerStates stat;
    /**
     * The number of bags that the passenger currently has
     */
    private int currentLuggage;

    /**
     * Instanciates a new PassengerInfo object
     * @param id The id of the passenger
     * @param inTransit True if the passenger is in transit
     * @param startingLuggage The number of the bags the passenger has
     */
    public PassengerInfo(int id, boolean inTransit, int startingLuggage) {
        this.id = id;
        this.inTransit = inTransit;
        this.startingLuggage = startingLuggage;
        this.currentLuggage = 0;
        this.stat = EPassengerStates.EXITING_THE_ARRIVAL_TERMINAL;
    }

    /**
     * Returns the id of the passenger
     * @return The ID of the passenger
     */
    public int getId() { 
    	return id;
    }
    
    /**
     * Updates the state of the passenger
     * @param stat The new state of the Passenger
     */
    public void setStat(EPassengerStates stat) {
        this.stat = stat;
    }

    /**
     * Returns the current luggage of the passenger
     * @return The current luggage of the passenger
     */
    public int getCurrentLuggage() {
        return currentLuggage;
    }

    /**
     * Increments the number of bags the passenger currently has
     */
    public void gotLuggage() {
        this.currentLuggage++;
    }

    /**
     * Returns the passengers's initial number of bags
     * @return The passengers's initial number of bags
     */
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
