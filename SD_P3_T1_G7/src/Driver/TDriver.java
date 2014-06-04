package Driver;

import java.rmi.RemoteException;

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 * Classe TDriver: classe de implementação da thread de condutor de autocarro
 */
public class TDriver extends Thread {
	
	private IDriverGenRep genRep;
	private IDriverArrivalTerminalTransferZone arrivalTerminalTransferZone;
	private IDriverBus bus;
	private int lastPassengers = 0;

	/**
	 * @param genRep
	 * Construtor da classe TDriver
	 */
	public TDriver(IDriverGenRep genRep, IDriverArrivalTerminalTransferZone arrivalTerminalTransferZone, IDriverBus bus) {
		this.arrivalTerminalTransferZone = arrivalTerminalTransferZone;
		this.bus = bus;
		this.genRep = genRep;
		try {
			genRep.registerDriver();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    /**
     * Implementação da máquina de estados da thread
     */
    public void run () {
		
		EDriverStates state = EDriverStates.PARKING_AT_THE_ARRIVAL_TERMINAL;
		EDriverStates nextState = state;
		boolean running = true;
		while (running) {
			switch (state) {
			case PARKING_AT_THE_ARRIVAL_TERMINAL:
				System.out.println("DRIVER: PARKING_AT_THE_ARRIVAL_TERMINAL");
				try {
					
					try {
						if (!(running = arrivalTerminalTransferZone.announcingBusBoaring(lastPassengers)))
							break;
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					lastPassengers = 0;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				System.out.println("DRIVER: PARKING_AT_THE_ARRIVAL_TERMINAL 2nd stage");
				
				try {
					bus.waitingForPassengers();
					arrivalTerminalTransferZone.announcingDeparture();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				nextState = EDriverStates.DRIVING_FORWARD;
				break;
				
			case DRIVING_FORWARD:
				System.out.println("DRIVER: DRIVING_FORWARD");

				nextState = EDriverStates.PARKING_AT_THE_DEPARTURE_TERMINAL;
				break;
				
			case PARKING_AT_THE_DEPARTURE_TERMINAL:
				System.out.println("DRIVER: PARKING_AT_THE_DEPARTURE_TERMINAL");

				try {
					//lastPassengers = bus.parkAndLetPassOff();
					lastPassengers = bus.parkAndLetPassOff();
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				nextState = EDriverStates.DRIVING_BACKWARD;
				break;
				
			case DRIVING_BACKWARD:
				System.out.println("DRIVER: DRIVING_BACKWARD");

				nextState = EDriverStates.PARKING_AT_THE_ARRIVAL_TERMINAL;
				break;
			}	
			state = nextState;
			try {
				genRep.updateDriverState(state);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Driver Dying!\n+++++++++++++++++++++++++++++++++++++++++++");
	}

}
