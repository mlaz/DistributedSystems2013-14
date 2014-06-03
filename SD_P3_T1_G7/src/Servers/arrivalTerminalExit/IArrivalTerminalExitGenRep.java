package Servers.arrivalTerminalExit;

/**
 * Interface para comunicação com MGenRep
 * @author miguel
 */
public interface IArrivalTerminalExitGenRep {

    /**
     *
     */
    public void setArrivalTerminalExit();

    /**
     *
     * @param busQueue
     */
    public void updateDriverQueue(int[] busQueue);
}
