package Servers.arrivalTerminal;

import java.rmi.Remote;

import Passenger.IPassengerArrivalTerminal;
import Porter.IPorterArrivalTerminal;

/**
 * This interface contains all the methods that MArrivalTerminal must implement.
 * 
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 */
public interface IArrivalTerminal extends IPassengerArrivalTerminal, IPorterArrivalTerminal, Remote {

}
