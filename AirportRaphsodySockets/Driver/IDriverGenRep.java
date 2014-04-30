package Driver;

public interface IDriverGenRep {

	void updateDriverState(EDriverStates state);

	IDriverArrivalTerminalTransferZone getArrivalTerminalExit();

	IDriverBus getBus();

	void registerDriver();

	void setDriverAsDead();

}
