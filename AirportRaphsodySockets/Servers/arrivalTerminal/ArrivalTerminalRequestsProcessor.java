package Servers.arrivalTerminal;
import Servers.Bag;
import Servers.clientsInterfaces.IRequestProcessor;
import messages.Message;

public class ArrivalTerminalRequestsProcessor implements IRequestProcessor {
	
	private final int[] validTypes = {Message.INT, Message.INT_INT};
	private MArrivalTerminal arrivalTerminal;
	
	public ArrivalTerminalRequestsProcessor( MArrivalTerminal arrivalTerminal ) {
		this.arrivalTerminal = arrivalTerminal; 
	}
	
	public Message processAndReply(Message fromClient) throws InterruptedException {
		
		fromClient.print();
		
		/* validate message */
		if( !isValidType(fromClient.getType()) ) {
			System.err.println("This message shouldn't be here");
		}
		
		Message fromServer = null;
		
		/* process and reply */
		switch( fromClient.getInt1() ) {
			case Message.TAKE_A_REST:
				boolean result = arrivalTerminal.takeARest();
				fromServer = new Message(Message.BOOL, result);
				break;
			case Message.WHAT_SHOULD_I_DO:
				arrivalTerminal.whatSouldIDo(fromClient.getInt2());
				fromServer = new Message(Message.ACK);
				break;
			case Message.TRY_TO_COLLECT_BAG:
				Bag bag = arrivalTerminal.tryToCollectABag();
				if( bag == null ) {
					fromServer = new Message(Message.INT_BOOL, -1, false);
				} else {
					fromServer = new Message(Message.INT_BOOL, bag.getPassNumber(), bag.isInTransit());
				}
				break;
			default:
				System.out.println("Unknown action! Nothing will be done.");
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
