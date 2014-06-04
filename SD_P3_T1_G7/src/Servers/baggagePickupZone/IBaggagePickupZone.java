package Servers.baggagePickupZone;

import java.rmi.Remote;

import Passenger.IPassengerBaggageCollectionPoint;
import Porter.IPorterBaggagePickupZone;

public interface IBaggagePickupZone extends IPorterBaggagePickupZone, IPassengerBaggageCollectionPoint, Remote {

}
