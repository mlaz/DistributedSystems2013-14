package Porter;

import java.rmi.RemoteException;

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 * Interface para interação entre a thread de bagageiro (TPorter) e o repositório geral (MGenRep) 
 */
public interface IPorterGenRep {

    /**
     *
     */
    void registerPorter() throws RemoteException;

    /**
     *
     */
    void removeLuggageAtPlane() throws RemoteException;

    /**
     *
     */
    void incLuggageAtCB() throws RemoteException;

    /**
     *
     */
    void incLuggageAtSR() throws RemoteException;

    /**
     *
     * @param state
     */
    void updatePorterState(EPorterStates state) throws RemoteException;

    /**
     *
     */
    void setPorterAsDead() throws RemoteException;

}
