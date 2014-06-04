package Servers.arrivalTerminalExit;

import java.rmi.Remote;

import Driver.IDriverArrivalTerminalTransferZone;
import Passenger.IPassengerArrivalExitTransferZone;

public interface IArrivalTerminalExit extends IDriverArrivalTerminalTransferZone, IPassengerArrivalExitTransferZone, Remote{

}
