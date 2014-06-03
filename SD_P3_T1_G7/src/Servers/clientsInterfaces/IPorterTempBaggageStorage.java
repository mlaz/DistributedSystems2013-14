package Servers.clientsInterfaces;

import Servers.Bag;

/**
 *
 * @author miguel
 */
public interface IPorterTempBaggageStorage {

    /**
     *
     * @param currentBag
     */
    void carryItToAppropriateStore(Bag currentBag);
}
