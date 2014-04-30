package Porter;

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 *
 */
public interface IPorterGenRep {

    /**
     *
     * @return
     */
    IPorterArrivalTerminal getArrivalTerminal();

    /**
     *
     * @return
     */
    IPorterBaggagePickupZone getBaggagePickupZone();

    /**
     *
     * @return
     */
    IPorterTempBaggageStorage getTempBaggageStorage();

    /**
     *
     */
    void registerPorter();

    /**
     *
     */
    void removeLuggageAtPlane();

    /**
     *
     */
    void incLuggageAtCB();

    /**
     *
     */
    void incLuggageAtSR();

    /**
     *
     * @param state
     */
    void updatePorterState(EPorterStates state);

    /**
     *
     */
    void setPorterAsDead();

}
