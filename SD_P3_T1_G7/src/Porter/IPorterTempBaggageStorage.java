package Porter;

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
    void carryItToAppropriateStore(Bag currentBag);
}
