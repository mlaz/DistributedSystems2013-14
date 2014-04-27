package Passenger;
import Server.baggageReclaimGuichet.MBaggageReclaimGuichet;
import Server.departureTerminalEntrance.MDepartureTerminalEntrance;

/**
 * 
 */

/**
 * @author Miguel Azevedo, Filipe Teixeira
 *
 */
public interface IPassengerGenRep {

	IPassengerArrivalTerminal getArrivalTerminal();

	IPassengerBaggageCollectionPoint getBaggagePickupZone();

	MBaggageReclaimGuichet getBaggageReclaimGuichet();

	IPassengerArrivalExitTransferZone getArrivalTerminalExit();

	MDepartureTerminalEntrance getDepartureTerminalEntrace();

	IPassengerBus getBus();

	void registerPassenger(int passengerNumber, int flightNumber,
			boolean inTransit, int remainingBags);

	void gotLuggage(int passengerNumber);

	void setPassengerStat(int passengerNumber, EPassengerStates state);

}
