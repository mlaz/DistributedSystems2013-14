
public interface IDriverGenRep {

	void updateDriverState(TDriver.states state);

	IDriverArrivalTerminalTransferZone getArrivalTerminalExit();

	IDriverBus getBus();

	void registerDriver();

}
