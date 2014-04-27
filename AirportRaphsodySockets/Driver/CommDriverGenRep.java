package Driver;

public class CommDriverGenRep implements IDriverGenRep {

	private IDriverArrivalTerminalTransferZone arrivalTerminalExit;
	private IDriverBus bus;
	
	public CommDriverGenRep() {
		arrivalTerminalExit = new CommDriverArrivalTerminalTransferZone();
		bus = new CommDriverBus();
	}
	
	@Override
	public void updateDriverState(EDriverStates state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IDriverArrivalTerminalTransferZone getArrivalTerminalExit() {
		return arrivalTerminalExit;
	}

	@Override
	public IDriverBus getBus() {
		return bus;
	}

	@Override
	public void registerDriver() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
		
	}

}
