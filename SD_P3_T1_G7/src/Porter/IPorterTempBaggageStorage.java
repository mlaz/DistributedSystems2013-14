package Porter;

import java.rmi.RemoteException;

import Utils.Bag;
import Utils.VectorClock;

/**
 * Interface para interação entre a thread de bagageiro (TPorter) e a zona de deposito temporário de bagagens (MTempBaggageStorage)
 * @author miguel
 */
public interface IPorterTempBaggageStorage {

    /**
     *
     * @param currentBag
     * @param vecClock 
     * @return 
     */
    VectorClock carryItToAppropriateStore(Bag currentBag, VectorClock vecClock) throws RemoteException;
}
