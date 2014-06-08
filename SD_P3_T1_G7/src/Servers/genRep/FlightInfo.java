package Servers.genRep;

/**
 * Class that stores the porter information. Used only for loggin porposes
 * 
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 */
class FlightInfo {
    /**
     * The ID of the current flight
     */
    private int flightID;
    /**
     * The number of bags in the flight
     */
    private int numLuggage;
    
    /**
     * Instanciates a FlightInfor object.
     * @param flightID The ID of the flight
     */
    public FlightInfo(int flightID) {
        this.flightID = flightID;
        numLuggage = 0;
        System.out.println("FlightId" + flightID);
    }

    @Override
    public String toString() {
        return " "+flightID+"  "+numLuggage;
    }

    /**
     * Returns the ID of the current flight
     * @return The ID of the current flight
     */
    public int getFlightID() {
        return flightID;
    }

    /**
     * Returns the number of bags
     * @return
     */
    public int getNumLuggage() {
        return numLuggage;
    }
    
    /**
     * Increments the number of bags
     * @param nbags The amount of bags to increment
     */
    public void addBaggage (int nbags) {
    	numLuggage += nbags;
    }
    
    /**
     * Removes one bag
     */
    public void removeABag () {
    	numLuggage--;
    }
    
    /**
     * Start a new flight
     * @param newID The ID of the new flight
     * @param newLuggageCount The amount of bags on the new flight
     */
    public void updateFlight(int newID, int newLuggageCount) {
        this.flightID = newID;
        this.numLuggage = newLuggageCount;
    }
}

