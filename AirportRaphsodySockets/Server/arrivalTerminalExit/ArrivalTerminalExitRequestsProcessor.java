package Server.arrivalTerminalExit;
import Server.clientsInterfaces.IRequestProcessor;
import messages.Message;

public class ArrivalTerminalExitRequestsProcessor implements IRequestProcessor {

	private final int[] validTypes = {Message.INT, Message.INT_INT};
	private MArrivalTerminalExit arrTermExit;
	
	
	public ArrivalTerminalExitRequestsProcessor(MArrivalTerminalExit arrTermExit) {
		this.arrTermExit = arrTermExit;
	}	

	public Message processAndReply(Message fromClient) throws InterruptedException {
		/* validate message */
		if( !isValidType(fromClient.getType()) ) {
			System.err.println("This message shouldn't be here");
		}
		
		Message toClient = null;
		
		/* process and reply */
		switch( fromClient.getInt1() ) {
			case Message.TAKE_A_BUS:
				arrTermExit.takeABus(fromClient.getInt2());
				toClient = new Message(Message.ACK);
				break;
			case Message.GO_HOME:
				arrTermExit.goHome(fromClient.getInt2());
				toClient = new Message(Message.ACK);
				break;
			case Message.ANNOUNCING_BUS_BOARDING:
				Boolean result = arrTermExit.announcingBusBoaring(fromClient.getInt2());
				toClient = new Message(Message.BOOL, result);
				break;
			case Message.ANNOUNCING_BUS_DEPARTURE:
				arrTermExit.announcingDeparture();
				toClient = new Message(Message.ACK);
				break;
			default:
				System.out.println("Unknown action! Exiting.");
				System.exit(1);
		}
		
		return toClient;
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
