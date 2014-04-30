package Servers.clientsInterfaces;
import Servers.EPassengerStates;
import Servers.ServerInfo;

/**
 * 
 */

/**
 * @author Miguel Azevedo, Filipe Teixeira
 *
 */
public interface IPassengerGenRep {

	ServerInfo getArrivalTerminal();

	ServerInfo getBaggagePickupZone();

	ServerInfo getBaggageReclaimGuichet();

	ServerInfo getArrivalTerminalExit();

	ServerInfo getDepartureTerminalEntrace();

	ServerInfo getBus();

	void registerPassenger(int passengerNumber, int flightNumber,
			boolean inTransit, int remainingBags);

	void gotLuggage(int passengerNumber);

	void setPassengerStat(int passengerNumber, EPassengerStates state);

}
