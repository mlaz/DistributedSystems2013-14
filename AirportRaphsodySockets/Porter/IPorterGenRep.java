package Porter;

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 *
 */
public interface IPorterGenRep {

	IPorterArrivalTerminal getArrivalTerminal();

	IPorterBaggagePickupZone getBaggagePickupZone();

	IPorterTempBaggageStorage getTempBaggageStorage();

	void registerPorter();

	void removeLuggageAtPlane();

	void incLuggageAtCB();

	void incLuggageAtSR();

	void updatePorterState(EPorterStates state);

}
