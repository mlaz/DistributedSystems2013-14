package Porter;

import java.rmi.RemoteException;

import Utils.VectorClock;

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
    void removeLuggageAtPlane(VectorClock clk) throws RemoteException;

    /**
     *
     */
    void incLuggageAtCB(VectorClock clk) throws RemoteException;

    /**
     *
     */
    void incLuggageAtSR(VectorClock clk) throws RemoteException;

    /**
     *
     * @param state
     */
    void updatePorterState(EPorterStates state, VectorClock clk) throws RemoteException;

    /**
     *
     */
    void setPorterAsDead() throws RemoteException;

	void porterWaiting4Plane() throws RemoteException;

}
