package Servers.baggagePickupZone;

import java.rmi.Remote;

import Passenger.IPassengerBaggageCollectionPoint;
import Porter.IPorterBaggagePickupZone;

/**
 * This interface contains all the methods that MBaggagePickupZone must implement.
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 */
public interface IBaggagePickupZone extends IPorterBaggagePickupZone, IPassengerBaggageCollectionPoint, Remote {

}
