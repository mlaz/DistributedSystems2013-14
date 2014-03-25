/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 */
class PassengerInfo {
    
    private int id;
    private String stat;
    private final String situation;
    private final int    startingLuggage;
    private int    currentLuggage;

    public PassengerInfo(int id, String stat, String situation, int startingLuggage) {
        this.id              = id;
        this.stat            = stat;
        this.situation       = situation;
        this.startingLuggage = startingLuggage;
        this.currentLuggage  = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public int getCurrentLuggage() {
        return currentLuggage;
    }

    public void setCurrentLuggage(int currentLuggage) {
        this.currentLuggage = currentLuggage;
    }

    public String getSituation() {
        return situation;
    }

    public int getStartingLuggage() {
        return startingLuggage;
    }

    @Override
    public String toString() {
        return stat + " " + situation + "  " + startingLuggage + "   " + currentLuggage + "  ";
    }
}
