package Servers.genRep;

import java.rmi.Remote;

import Driver.IDriverGenRep;
import Passenger.IPassengerGenRep;
import Porter.IPorterGenRep;

public interface IGenRep extends IPassengerGenRep, IDriverGenRep,
IPorterGenRep, Remote{

	int getNumFlights();

	int getNumPassengers();

	int getMaxBags();

	void waitForPorterToDie();

	void endSimulation();

	void waitForDriverToDie();

}
