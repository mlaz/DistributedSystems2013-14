package Servers.bus;

import java.rmi.Remote;
import Driver.IDriverBus;
import Passenger.IPassengerBus;

public interface IBus extends IPassengerBus, IDriverBus, Remote {

}
