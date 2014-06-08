package Driver;


/**
 * Enum data type with all the states the driver can be in.
 * 
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 */
public enum EDriverStates {
 
    /**
     *	The driver is parked at the arrival terminal
     */
    PARKING_AT_THE_ARRIVAL_TERMINAL,

    /**
     * The driver is driving forward
     */
    DRIVING_FORWARD,

    /**
     * The driver is at the departure terminal
     */
    PARKING_AT_THE_DEPARTURE_TERMINAL,

    /**
     * The driver is driving backwards
     */
    DRIVING_BACKWARD
}
