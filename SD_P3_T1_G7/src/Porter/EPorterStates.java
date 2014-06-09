package Porter;

/**
 * Enum with all the states the porter may be in
 * 
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 */
public enum EPorterStates {

    /**
     * The porter is waiting for a new plane to land
     */
    WAITING_FOR_A_PLANE_TO_LAND,

    /**
     * The porter is at the planes hold
     */
    AT_THE_PLANES_HOLD,

    /**
     * The porter is at the luggage conveyor belt
     */
    AT_THE_LUGGAGE_BELT_CONVEYOR,

    /**
     * The porter is at the temporary storage
     */
    AT_THE_STOREROOM
}
