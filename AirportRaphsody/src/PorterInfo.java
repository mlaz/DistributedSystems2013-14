/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 */
class PorterInfo {
    private String stat;
    private int cb;
    private int sr;

    public PorterInfo(String state, int cb, int sr) {
        this.stat = state;
        this.cb   = cb;
        this.sr   = sr;
    }

    public String getStat() {
        return stat;
    }

    public int getCb() {
        return cb;
    }

    public int getSr() {
        return sr;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public void setCb(int cb) {
        this.cb = cb;
    }

    public void setSr(int sr) {
        this.sr = sr;
    }
    
    @Override
    public String toString() {
        return " " + stat + "  " + cb + "  " + sr + " "  ;
    }
}
