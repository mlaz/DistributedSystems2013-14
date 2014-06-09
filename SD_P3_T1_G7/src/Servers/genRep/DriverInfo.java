package Servers.genRep;

import Driver.EDriverStates;

/**
 * Class that stores the driver information. Used only for logging porposes
 * 
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 */
public class DriverInfo {
    /**
     * The state of the driver
     */
    private EDriverStates stat;
    /**
     * Who's in the queue
     */
    private int[]  queueIDs;
    /**
     * Who's in the bus
     */
    private int[]  seatsIDs;
    /**
     * True if the driver is dead.
     */
    private boolean dead;
    
    /**
     * Instanciates a DriverInfo object.
     * @param queueSize The size of the queue to wait for the bus
     * @param numSeats The number of seats on the bus
     */
    public DriverInfo(int queueSize, int numSeats) {
    	
        this.queueIDs = new int[queueSize];
        this.seatsIDs = new int[numSeats];
        this.stat = EDriverStates.PARKING_AT_THE_ARRIVAL_TERMINAL;
        for( int i=0 ; i<queueIDs.length ; i++ ) {
            queueIDs[i] = -1;
        }
        
        for( int i=0 ; i<seatsIDs.length ; i++ ) {
            seatsIDs[i] = -1;
        }
        
        dead = false;
    }

    /**
     * Updates the state of the driver
     * @param stat
     */
    public void setStat(EDriverStates stat) {
        this.stat = stat;
    }

    /**
     * Updates the queue
     * @param queueIDs
     */
    public void setQueueIDs(int[] queueIDs) {
        this.queueIDs = queueIDs;
    }

    /**
     * Updates the bus seats
     * @param seatsIDs
     */
    public void setSeatsIDs(int[] seatsIDs) {
        this.seatsIDs = seatsIDs;
    }

    /**
     * Set the driver as dead.
     */
    public void setAsDead() {
    	dead = true;
    }
    
    /**
     * Returs true if the driver is dead
     * @return True if the driver is dead
     */
    public boolean isDead() {
    	return dead;
    }
    
    @Override
    public String toString() {
    	String stat;
    	
    	switch (this.stat) {
    	 case PARKING_AT_THE_ARRIVAL_TERMINAL:
    		 stat = "PAAT";
    		 break;
    	 case DRIVING_FORWARD:
    		 stat = "DRFW";
    		 break;
    	 case PARKING_AT_THE_DEPARTURE_TERMINAL:
    		 stat = "PADT";
    		 break;
    	 default:
    		 stat = "DRBW";
    		 break;
    	}
    	
        String s ="  "+ stat + "  ";
        
        s += stringIDs(queueIDs);
        s += " ";
        s += stringIDs(seatsIDs);
        
        return s;
    }
    
    private String stringIDs(int[] ids) {
        String s = "";
        
        for(int i=0 ; i<ids.length ; i++) {
            if( ids[i] == -1 )
                s += " - ";
            else
                s += " " + ids[i] + " ";
        }
        
        return s;
    }
}
