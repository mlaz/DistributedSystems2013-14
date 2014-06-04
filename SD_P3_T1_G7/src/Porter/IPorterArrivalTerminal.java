package Porter;

import java.rmi.RemoteException;

import Utils.Bag;

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
     * @return
     * @throws InterruptedException
     */
    public boolean takeARest () throws InterruptedException, RemoteException;

    /**
     *
     * @return
     */
    public Bag tryToCollectABag () throws RemoteException;

}
