package Porter;

import java.rmi.RemoteException;

import Utils.Bag;
import Utils.ClockTuple;
import Utils.VectorClock;

/**
 *  IPorteArrivalTerminal - interface
 */

/**
 * Interface for communications between the Porter and the Arrival Terminal
 *  
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 */
public interface IPorterArrivalTerminal {

    /**
     * The porter rests and waits for a new plane
     * @param vecClock The clock 
     * @return The clock
     * @throws InterruptedException
     */
    public ClockTuple<Boolean> takeARest (VectorClock vecClock) throws InterruptedException, RemoteException;

    /**
     * The porter collects a bag from the plane.
     * @param vecClock The clock
     * @return The clock
     */
    public ClockTuple<Bag> tryToCollectABag (VectorClock vecClock) throws RemoteException;

}
