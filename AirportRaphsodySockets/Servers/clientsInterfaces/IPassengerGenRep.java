package Servers.clientsInterfaces;
import Servers.EPassengerStates;
import Servers.ServerInfo;

/**
 * 
 */

/**
 * @author Miguel Azevedo, Filipe Teixeira
 *
 */
public interface IPassengerGenRep {

    /**
     *
     * @return
     */
    ServerInfo getArrivalTerminal();

    /**
     *
     * @return
     */
    ServerInfo getBaggagePickupZone();

    /**
     *
     * @return
     */
    ServerInfo getBaggageReclaimGuichet();

    /**
     *
     * @return
     */
    ServerInfo getArrivalTerminalExit();

    /**
     *
     * @return
     */
    ServerInfo getDepartureTerminalEntrace();

    /**
     *
     * @return
     */
    ServerInfo getBus();

    /**
     *
     * @param passengerNumber
     * @param flightNumber
     * @param inTransit
     * @param remainingBags
     */
    void registerPassenger(int passengerNumber, int flightNumber,
			boolean inTransit, int remainingBags);

    /**
     *
     * @param passengerNumber
     */
    void gotLuggage(int passengerNumber);

    /**
     *
     * @param passengerNumber
     * @param state
     */
    void setPassengerStat(int passengerNumber, EPassengerStates state);

}
