package Servers.tempStorage;

import java.rmi.Remote;

import Porter.IPorterTempBaggageStorage;

/**
 * This interface contains all the methods that MTempBaggageStorage must implement.
 * 
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 */
public interface ITempStorage extends IPorterTempBaggageStorage, Remote {

}
