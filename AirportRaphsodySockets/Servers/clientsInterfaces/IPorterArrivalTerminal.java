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
	
	public boolean takeARest () throws InterruptedException;
	public Bag tryToCollectABag ();

}
