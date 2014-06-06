package Porter;

import java.rmi.RemoteException;

import Utils.Bag;
import Utils.ClockTuple;
import Utils.VectorClock;

/**
 *  IPorteArrivalTerminal - interface
 */

/**
 * @author miguel
 * Interface para interação entre a thread de bagageiro (TPorter) e o terminal de chagada (MArrivalTerminal) 
 */
public interface IPorterArrivalTerminal {

    /**
     *
     * @param vecClock 
     * @return
     * @throws InterruptedException
     */
    public ClockTuple<Boolean> takeARest (VectorClock vecClock) throws InterruptedException, RemoteException;

    /**
     *
     * @param vecClock 
     * @return
     */
    public ClockTuple<Bag> tryToCollectABag (VectorClock vecClock) throws RemoteException;

}
