package Driver;

/**
 *
 * @author miguel
 */
public interface IDriverGenRep {

    /**
     *
     * @param state
     */
    void updateDriverState(EDriverStates state);

    /**
     *
     * @return
     */
    IDriverArrivalTerminalTransferZone getArrivalTerminalExit();

    /**
     *
     * @return
     */
    IDriverBus getBus();

    /**
     *
     */
    void registerDriver();

    /**
     *
     */
    void setDriverAsDead();

}
