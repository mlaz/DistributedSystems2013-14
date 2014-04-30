package Servers.clientsInterfaces;
import Servers.EPorterStates;
import Servers.ServerInfo;

/**
 * 
 */

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 *
 */
public interface IPorterGenRep {

    /**
     *
     * @return
     */
    ServerInfo getArrivalTerminal();

    /**
     *
     * @return
     */
    ServerInfo getBaggagePickupZone();

    /**
     *
     * @return
     */
    ServerInfo getTempBaggageStorage();

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

}
