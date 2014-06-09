package Porter;

import java.rmi.RemoteException;

import Utils.VectorClock;

/**
 * Interface for communications between the Porter and the General Repository
 * 
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 */
public interface IPorterGenRep {

    /**
     * Registers the porter.
     * @throws RemoteException
     */
    void registerPorter() throws RemoteException;

    /**
     * Removes one bag from the plane
     * @param clk The clock
     * @throws RemoteException
     */
    void removeLuggageAtPlane(VectorClock clk) throws RemoteException;

    /**
     * Puts a bag in the conveyor belt
     * @param clk The clock
     * @throws RemoteException
     */
    void incLuggageAtCB(VectorClock clk) throws RemoteException;

    /**
     * Puts a bag in the temporary storage room
     * @param clk The clock
     * @throws RemoteException
     */
    void incLuggageAtSR(VectorClock clk) throws RemoteException;

    /**
     * Updates the porter state
     * 
     * @param state The new porter state
     * @param clk The clock
     * @throws RemoteException
     */
    void updatePorterState(EPorterStates state, VectorClock clk) throws RemoteException;

    /**
     * Defines the porter as dead, no more work to be done.
     * @throws RemoteException
     */
    void setPorterAsDead() throws RemoteException;

	/**
	 * Blocks waiting for a new plane.
	 * @throws RemoteException
	 */
	void porterWaiting4Plane() throws RemoteException;

}
