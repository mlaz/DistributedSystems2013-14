package Servers.bus;

import Servers.clientsInterfaces.IRequestProcessor;
import messages.Message;

/**
 * Classe Para processameto de mensagens para o monitor MBus
 * @author miguel
 */
public class BusRequestsProcessor implements IRequestProcessor {
	private final int[] validTypes = {Message.INT, Message.INT_INT};
	private MBus bus;

    /**
     *
     * @param bus
     */
    public BusRequestsProcessor( MBus bus ) {
		this.bus = bus; 
	}

    /**
     *
     * @param fromClient
     * @return
     * @throws InterruptedException
     */
    public Message processAndReply(Message fromClient) throws InterruptedException {
		
		fromClient.print();
		
		/* validate message */
		if( !isValidType(fromClient.getType()) ) {
			System.err.println("This message shouldn't be here");
		}
		
		Message fromServer = null;
		
		/* process and reply */
		switch( fromClient.getInt1() ) {
			case Message.ENTER_THE_BUS:
				boolean result = bus.enterTheBus(fromClient.getInt2());
				fromServer = new Message(Message.BOOL, result);
				break;
			case Message.LEAVE_THE_BUS:
				bus.leaveTheBus(fromClient.getInt2());
				fromServer = new Message(Message.ACK);
				break;
			case Message.PARK_LET_OFF:
				int res = bus.parkAndLetPassOff();
				fromServer = new Message(Message.INT, res);
				break;
			case Message.WAIT_FOR_PASSENGERS:
				bus.waitingForPassengers();
				fromServer = new Message(Message.ACK);
				break;
			default:
				System.out.println("Unknown action! Exiting.");
				System.exit(1);
		}
		
		return fromServer;
	}
	
	private boolean isValidType(int type) {
		return contains( validTypes, type );
	}
	
	private boolean contains(int[] list, int value) {
		for(int v:list) {
			if( value == v ) {
				return true;
			}
		}
		
		return false;
	}

}
