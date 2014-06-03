package Servers.baggageReclaimGuichet;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface para comunicação com MGenRep
 * @author miguel
 */
public interface IBaggageReclaimGuichetGenRep extends Remote {

    /**
     *
     */
    void setBaggageReclaimGuichet() throws RemoteException;

}
