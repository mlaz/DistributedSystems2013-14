/**
 * 
 */

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 *
 */
public interface IPorterGenRep {

	IPorterArrivalTerminal getArrivalTerminal();

	IPorterBaggagePickupZone getBaggagePickupZone();

	MTempBaggageStorage getTempBaggageStorage();

	void registerPorter();

	void removeLuggageAtPlane();

	void incLuggageAtCB();

	void incLuggageAtSR();

	void updatePorterState(TPorter.states state);

}
