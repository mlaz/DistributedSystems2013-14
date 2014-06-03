package Servers.clientsInterfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import Servers.EDriverStates;
import Servers.ServerInfo;

/**
 *
 * @author miguel
 */
public interface IDriverGenRep extends Remote {

    /**
     *
     * @param state
     */
    void updateDriverState(EDriverStates state) throws RemoteException;

    /**
     *
     * @return
     */
    ServerInfo getArrivalTerminalExit() throws RemoteException;

    /**
     *
     * @return
     */
    ServerInfo getBus() throws RemoteException;

    /**
     *
     */
    void registerDriver() throws RemoteException;

}
