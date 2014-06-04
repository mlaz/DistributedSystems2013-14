package Servers.genRep;

import java.rmi.Remote;
import java.rmi.RemoteException;

import Driver.IDriverGenRep;
import Passenger.IPassengerGenRep;
import Porter.IPorterGenRep;

public interface IGenRep extends IPassengerGenRep, IDriverGenRep,
IPorterGenRep, Remote {

	int getNumFlights() throws RemoteException;

	int getNumPassengers() throws RemoteException;

	int getMaxBags() throws RemoteException;

	void waitForPorterToDie() throws RemoteException;

	void waitForDriverToDie() throws RemoteException;
	
	void endSimulation() throws RemoteException;

	int getNumSeats() throws RemoteException;
}
