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

	MDepartureTerminalEntrace getDepartureTerminalEntrace();

	IPassengerBus getBus();

	void registerPassenger(int passengerNumber, int flightNumber,
			boolean inTransit, int remainingBags);

	void gotLuggage(int passengerNumber);

	void setPassengerStat(int passengerNumber, TPassenger.states state);

}
