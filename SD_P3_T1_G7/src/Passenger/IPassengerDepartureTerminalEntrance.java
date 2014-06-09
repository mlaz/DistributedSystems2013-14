package Passenger;

import java.rmi.RemoteException;

import Utils.VectorClock;

/**
 * Interface for communications between a Passenger and the Departure Terminal Entrance
 * 
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 */
public interface IPassengerDepartureTerminalEntrance {

    /**
     * The passenger blocks waits for all the passengers to call this method.
     * 
     * @param vecClock The clock
     * @return The clock
     * @throws InterruptedException
     * @throws RemoteException
     */
    public VectorClock prepareNextLeg(VectorClock vecClock) throws InterruptedException, RemoteException;
}