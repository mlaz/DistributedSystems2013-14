
import java.util.LinkedList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 */
class FlightInfo {
    private int flightID;
    private int numLuggage;
    
    public FlightInfo(int flightID, int numLuggage) {
        this.flightID      = flightID;
        this.numLuggage    = numLuggage;
    }

    @Override
    public String toString() {
        return " "+flightID+"  "+numLuggage;
    }

    public int getFlightID() {
        return flightID;
    }

    public int getNumLuggage() {
        return numLuggage;
    }
    
    public void updateFlight(int newID, int newLuggageCount) {
        this.flightID = newID;
        this.numLuggage = newLuggageCount;
    }
}

