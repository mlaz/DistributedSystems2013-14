package Driver;

import java.rmi.RemoteException;

import Utils.VectorClock;


/**
 * Interface of the Driver with the General Repository
 * 
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 */
public interface IDriverGenRep {

    /**
     * Updates the driver state on the general repository
     * @param state The new state
     * @param clk The clock
     * @throws RemoteException
     */
    void updateDriverState(EDriverStates state, VectorClock clk) throws RemoteException;

    /**
     * Registers the driver on the general repository.
     * @throws RemoteException
     */
    void registerDriver() throws RemoteException;

    /**
     * Registers the driver as dead.
     * @throws RemoteException
     */
    void setDriverAsDead() throws RemoteException;

	/**
	 * Blocks and informs the general repository that the driver is ready for a new plane 
	 * @throws RemoteException
	 */
	void driverWaiting4Plane() throws RemoteException;

}
