package Porter;

import java.rmi.RemoteException;

import Utils.Bag;

/**
 * Interface para interação entre a thread de bagageiro (TPorter) e a zona de deposito temporário de bagagens (MTempBaggageStorage)
 * @author miguel
 */
public interface IPorterTempBaggageStorage {

    /**
     *
     * @param currentBag
     */
    void carryItToAppropriateStore(Bag currentBag) throws RemoteException;
}
