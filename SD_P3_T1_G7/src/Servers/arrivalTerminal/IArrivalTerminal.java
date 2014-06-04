package Servers.arrivalTerminal;

import java.rmi.Remote;

import Passenger.IPassengerArrivalTerminal;
import Porter.IPorterArrivalTerminal;

public interface IArrivalTerminal extends IPassengerArrivalTerminal, IPorterArrivalTerminal, Remote {

}
