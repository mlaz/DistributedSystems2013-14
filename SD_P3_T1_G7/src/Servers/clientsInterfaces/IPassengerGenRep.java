package Servers.clientsInterfaces;
import java.rmi.Remote;
import java.rmi.RemoteException;

import Servers.EPassengerStates;
import Servers.ServerInfo;

/**
 * 
 */

/**
 * @author Miguel Azevedo, Filipe Teixeira
 *
 */
public interface IPassengerGenRep extends Remote {

    /**
     *
     * @return
     */
    public ServerInfo getArrivalTerminal() throws RemoteException;

    /**
     *
     * @return
     */
    public ServerInfo getBaggagePickupZone() throws RemoteException;

    /**
     *
     * @return
     */
    public ServerInfo getBaggageReclaimGuichet() throws RemoteException;

    /**
     *
     * @return
     */
    public ServerInfo getArrivalTerminalExit() throws RemoteException;

    /**
     *
     * @return
     */
    public ServerInfo getDepartureTerminalEntrace() throws RemoteException;

    /**
     *
     * @return
     */
    public ServerInfo getBus() throws RemoteException;

    /**
     *
     * @param passengerNumber
     * @param flightNumber
     * @param inTransit
     * @param remainingBags
     */
    public void registerPassenger(int passengerNumber, int flightNumber,
			boolean inTransit, int remainingBags) throws RemoteException;

    /**
     *
     * @param passengerNumber
     */
    public void gotLuggage(int passengerNumber) throws RemoteException;

    /**
     *
     * @param passengerNumber
     * @param state
     */
    public void setPassengerStat(int passengerNumber, EPassengerStates state) throws RemoteException;

}
