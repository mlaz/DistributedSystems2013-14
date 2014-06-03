package Servers.clientsInterfaces;
import java.rmi.Remote;
import java.rmi.RemoteException;

import Servers.EPorterStates;
import Servers.ServerInfo;

/**
 * 
 */

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 *
 */
public interface IPorterGenRep extends Remote {

    /**
     *
     * @return
     */
    ServerInfo getArrivalTerminal() throws RemoteException;

    /**
     *
     * @return
     */
    ServerInfo getBaggagePickupZone() throws RemoteException;

    /**
     *
     * @return
     */
    ServerInfo getTempBaggageStorage() throws RemoteException;

    /**
     *
     */
    void registerPorter() throws RemoteException;

    /**
     *
     */
    void removeLuggageAtPlane() throws RemoteException;

    /**
     *
     */
    void incLuggageAtCB() throws RemoteException;

    /**
     *
     */
    void incLuggageAtSR() throws RemoteException;

    /**
     *
     * @param state
     */
    void updatePorterState(EPorterStates state) throws RemoteException;

}
