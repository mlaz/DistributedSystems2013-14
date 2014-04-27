package Server.clientsInterfaces;

import Server.Bag;

/**
 *  IPorteArrivalTerminal - interface
 */

/**
 * @author miguel
 *
 */
public interface IPorterArrivalTerminal {
	
	public boolean takeARest () throws InterruptedException;
	public Bag tryToCollectABag ();

}
