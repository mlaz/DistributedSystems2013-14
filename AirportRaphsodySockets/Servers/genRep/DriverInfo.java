package Servers.genRep;

import Servers.EDriverStates;

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
public class DriverInfo {
    private EDriverStates stat;
    private int[]  queueIDs;
    private int[]  seatsIDs;
    private boolean dead;
    
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

    public void setStat(EDriverStates stat) {
        this.stat = stat;
    }

    public void setQueueIDs(int[] queueIDs) {
        this.queueIDs = queueIDs;
    }

    public void setSeatsIDs(int[] seatsIDs) {
        this.seatsIDs = seatsIDs;
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
