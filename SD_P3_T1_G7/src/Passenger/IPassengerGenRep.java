package Passenger;

import java.rmi.RemoteException;

import Utils.VectorClock;


/**
 * Interface for communications between a Passenger and the General Repository
 * 
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 */
public interface IPassengerGenRep {

    /**
     * Registers the passenger.
     * 
     * @param passengerNumber The id of this passenger
     * @param flightNumber The number of the flight this passenger is in
     * @param inTransit True if the passenger is in transit
     * @param remainingBags The number of the bags the passenger has
     * @throws RemoteException
     */
    void registerPassenger(int passengerNumber, int flightNumber,
			boolean inTransit, int remainingBags) throws RemoteException;

    /**
     * The passenger acquires a new bag from the conveyor belt
     * @param passengerNumber The id of this passenger
     * @param clk The clock
     * @throws RemoteException
     */
    void gotLuggage(int passengerNumber, VectorClock clk) throws RemoteException;


    /**
     * The passenger updates its state.
     * 
     * @param passengerNumber The id of this passenger
     * @param state The new state
     * @param clk The clock
     * @throws RemoteException
     */
    void setPassengerStat(int passengerNumber, EPassengerStates state, VectorClock clk) throws RemoteException;

}
