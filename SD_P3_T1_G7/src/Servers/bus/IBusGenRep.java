package Servers.bus;

/**
 * Interface para comunicação com MGenRep
 * @author miguel
 */
public interface IBusGenRep {

    /**
     *
     */
    void setBus();

    /**
     *
     * @param seats
     */
    void updateDriverSeats(int[] seats);
}
