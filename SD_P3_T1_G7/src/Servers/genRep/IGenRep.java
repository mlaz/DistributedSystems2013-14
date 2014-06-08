package Servers.genRep;

import java.rmi.Remote;
import java.rmi.RemoteException;

import Driver.IDriverGenRep;
import Passenger.IPassengerGenRep;
import Porter.IPorterGenRep;
import Servers.arrivalTerminalExit.IArrivalTerminalExitGenRep;
import Servers.bus.IBusGenRep;

/**
 * This interface contains all the methods that MGenRep must implement.
 * 
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 */
public interface IGenRep extends IPassengerGenRep, IDriverGenRep,
IPorterGenRep, IBusGenRep, IArrivalTerminalExitGenRep, Remote {

	/**
	 * Returns the number of flights of the simulation
	 * @return The number of flights of the simulation
	 * @throws RemoteException
	 */
	int getNumFlights() throws RemoteException;

	/**
	 * Returns the number of passengers in each flight
	 * @return The number of passengers in each flight
	 * @throws RemoteException
	 */
	int getNumPassengers() throws RemoteException;

	/**
	 * Returns the maximum number of bags each passengers can have
	 * @return The maximum number of bags each passengers can have
	 * @throws RemoteException
	 */
	int getMaxBags() throws RemoteException;

	/**
	 * Bloks the caller until the porter dies
	 * @throws RemoteException
	 */
	void waitForPorterToDie() throws RemoteException;

	/**
	 * Bloks the caller until the driver dies
	 * @throws RemoteException
	 */
	void waitForDriverToDie() throws RemoteException;
	
	/**
	 * Blocks the caller until the driver and porter are done with the current flight
	 * @throws RemoteException
	 */
	void planeFinished() throws RemoteException;
	
	/**
	 * Used to indicate that everything is done and the GenRep may close the log.
	 * @throws RemoteException
	 */
	void endSimulation() throws RemoteException;

	/**
	 * Returns the number of seats on the bus
	 * @return The number of seats on the bus
	 * @throws RemoteException
	 */
	int getNumBusSeats() throws RemoteException;
	
	/**
	 * Returns the time, in mili-seconds, that the bus waits for more passengers.
	 * @return The time, in mili-seconds, that the bus waits for more passengers.
	 * @throws RemoteException
	 */
	int getBusWaitTime() throws RemoteException;
	
	/**
	 * Registers the new service's location on the General Repository.
	 * @param serviceName The name of the service to be registered
	 * @param hostname The name of the machine hosting the new service
	 * @throws RemoteException
	 */
	void registerService(String serviceName, String hostname) throws RemoteException;
	
	/**
	 * Returns the location of the requested service
	 * @param serviceName The name of the service
	 * @return The location of the service
	 * @throws RemoteException
	 */
	String getServiceLocation(String serviceName) throws RemoteException;
}
