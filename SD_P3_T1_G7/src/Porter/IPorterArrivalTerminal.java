package Porter;

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
    public boolean takeARest () throws InterruptedException;

    /**
     *
     * @return
     */
    public Bag tryToCollectABag ();

}
