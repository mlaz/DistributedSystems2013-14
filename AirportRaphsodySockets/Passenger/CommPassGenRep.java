package Passenger;

import Server.MBaggageReclaimGuichet;
import Server.MDepartureTerminalEntrace;

public class CommPassGenRep implements IPassengerGenRep{

	@Override
	public IPassengerArrivalTerminal getArrivalTerminal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPassengerBaggageCollectionPoint getBaggagePickupZone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MBaggageReclaimGuichet getBaggageReclaimGuichet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPassengerArrivalExitTransferZone getArrivalTerminalExit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MDepartureTerminalEntrace getDepartureTerminalEntrace() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPassengerBus getBus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registerPassenger(int passengerNumber, int flightNumber,
			boolean inTransit, int remainingBags) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gotLuggage(int passengerNumber) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPassengerStat(int passengerNumber, EPassengerStates state) {
		// TODO Auto-generated method stub
		
	}

}
