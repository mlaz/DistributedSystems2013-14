package Servers.bus;

import java.rmi.Remote;
import Driver.IDriverBus;
import Passenger.IPassengerBus;

/**
 * This interface contains all the methods that MBus must implement.
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 */
public interface IBus extends IPassengerBus, IDriverBus, Remote {

}
