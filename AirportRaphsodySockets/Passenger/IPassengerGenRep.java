package Passenger;


/**
 * @author Miguel Azevedo, Filipe Teixeira
 *
 */
public interface IPassengerGenRep {

	IPassengerArrivalTerminal getArrivalTerminal();

	IPassengerBaggageCollectionPoint getBaggagePickupZone();

	IPassengerBaggageReclaimGuichet getBaggageReclaimGuichet();

	IPassengerArrivalExitTransferZone getArrivalTerminalExit();

	IPassengerDepartureTerminalEntrance getDepartureTerminalEntrace();

	IPassengerBus getBus();

	void registerPassenger(int passengerNumber, int flightNumber,
			boolean inTransit, int remainingBags);

	void gotLuggage(int passengerNumber);

	void setPassengerStat(int passengerNumber, EPassengerStates state);

}
