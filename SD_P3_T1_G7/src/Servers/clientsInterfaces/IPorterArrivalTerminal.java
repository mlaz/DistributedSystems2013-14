package Servers.clientsInterfaces;

import Servers.Bag;

/**
 *  IPorteArrivalTerminal - interface
 */

/**
 * @author miguel
 *
 */
public interface IPorterArrivalTerminal {

    /**
     *
     * @return
     * @throws InterruptedException
     */
    public boolean takeARest () throws InterruptedException;

    /**
     *
     * @return
     */
    public Bag tryToCollectABag ();

}
