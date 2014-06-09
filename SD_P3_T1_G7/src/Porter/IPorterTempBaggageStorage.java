package Porter;

import java.rmi.RemoteException;

import Utils.Bag;
import Utils.VectorClock;

/**
 * Interface for communication between the Porter and the Temporary Baggage Storage
 * 
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 */
public interface IPorterTempBaggageStorage {

    /**
     * The porter carries the bag to appropriate destination, Converyor Belt of Temporary Storage.
     * @param currentBag The Bag that is being carried
     * @param vecClock The clock
     * @return The clock
     */
    VectorClock carryItToAppropriateStore(Bag currentBag, VectorClock vecClock) throws RemoteException;
}
