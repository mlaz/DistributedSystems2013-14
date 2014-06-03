package Servers.tempStorage;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface para comunicação com MGenRep
 * @author miguel
 */
public interface ITempBaggageStorageGenRep extends Remote {

    /**
     *
     */
    void setTempStorage() throws RemoteException;

}
