package Servers.arrivalTerminalExit;

import java.rmi.Remote;

import Driver.IDriverArrivalTerminalTransferZone;
import Passenger.IPassengerArrivalExitTransferZone;

/**
 * This interface contains all the methods that MArrivalTerminalExit must implement.
 * 
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 */
public interface IArrivalTerminalExit extends IDriverArrivalTerminalTransferZone, IPassengerArrivalExitTransferZone, Remote{

}
