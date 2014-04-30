package Servers.clientsInterfaces;

import Servers.EDriverStates;
import Servers.ServerInfo;

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
    ServerInfo getArrivalTerminalExit();

    /**
     *
     * @return
     */
    ServerInfo getBus();

    /**
     *
     */
    void registerDriver();

}
