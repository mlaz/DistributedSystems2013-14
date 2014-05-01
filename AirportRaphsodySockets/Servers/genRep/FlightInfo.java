package Servers.genRep;

//import java.util.LinkedList;

/**
 * Classe para logging de informação de cada voo
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 */
class FlightInfo {
    private int flightID;
    private int numLuggage;
    public FlightInfo(int flightID) {
        this.flightID = flightID;
        numLuggage = 0;
        System.out.println("FlightId" + flightID);
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
    
    public void addBaggage (int nbags) {
    	numLuggage += nbags;
    }
    
    public void removeABag () {
    	numLuggage--;
    }
    
    public void updateFlight(int newID, int newLuggageCount) {
        this.flightID = newID;
        this.numLuggage = newLuggageCount;
    }
}

