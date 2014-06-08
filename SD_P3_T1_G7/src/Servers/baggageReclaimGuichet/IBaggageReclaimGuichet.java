package Servers.baggageReclaimGuichet;

import java.rmi.Remote;

import Passenger.IPassengerBaggageReclaimGuichet;

/**
 * This interface contains all the methods that MBaggageReclaimGuichet must implement.
 * 
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 */
public interface IBaggageReclaimGuichet extends IPassengerBaggageReclaimGuichet, Remote {

}
