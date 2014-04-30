package Servers.departureTerminalEntrance;

import Servers.clientsInterfaces.IRequestProcessor;
import messages.Message;

/**
 *
 * @author miguel
 */
public class DepartureTerminalEntranceRequestsProcessor implements IRequestProcessor {
	private final int[] validTypes = {Message.INT};
	private MDepartureTerminalEntrance departEntrance;

    /**
     *
     * @param departEntrance
     */
    public DepartureTerminalEntranceRequestsProcessor(MDepartureTerminalEntrance departEntrance) {
		this.departEntrance = departEntrance;
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
			case Message.PREPARE_NEXT_LEG:
				departEntrance.prepareNextLeg();
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
