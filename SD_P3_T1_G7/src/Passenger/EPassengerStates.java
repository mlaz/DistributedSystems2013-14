package Passenger;

/**
 * Enum with all the states the passenger may be in.
 * 
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 */
public enum EPassengerStates {
 
    /**
     * The passenger is at the disembarking zone
     */
    AT_THE_DISEMBARKING_ZONE,

    /**
     * The passenger is at the luggage collection point
     */
    AT_THE_LUGGAGE_COLLECTION_POINT,
 
    /**
     * The passenger is at the baggage reclaim office
     */
    AT_THE_BAGGAGE_RECLAIM_OFFICE,

    /**
     * The passenger is at the arrival terminal
     */
    EXITING_THE_ARRIVAL_TERMINAL,

    /**
     * The passenger is at the arrival terminal exit
     */
    AT_THE_ARRIVAL_TRANSFER_TERMINAL,

    /**
     * The passenger is in the bus
     */
    TERMINAL_TRANSFER,

    /**
     * The passenger is exiting the bus
     */
    AT_THE_DEPARTURE_TRANSFER_TERMINAL,

    /**
     * The passenger is entering the departure terminal
     */
    ENTERING_THE_DEPARTURE_TERMINAL
}
