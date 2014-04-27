package Server.baggagePickupZone;

import Server.clientsInterfaces.IRequestProcessor;
import messages.Message;

public class BaggagePickupZoneRequestsProcessor implements IRequestProcessor {

	private final int[] validTypes = {Message.INT, Message.INT_INT, Message.INT_INT_INT};
	private MBaggagePickupZone baggagePickup;
	
	public BaggagePickupZoneRequestsProcessor( MBaggagePickupZone baggagePickup ) {
		this.baggagePickup = baggagePickup; 
	}
	
	public Message processAndReply(Message fromClient) throws InterruptedException {
		/* validate message */
		if( !isValidType(fromClient.getType()) ) {
			System.err.println("This message shouldn't be here");
		}
		
		Message fromServer = null;
		boolean result;
		/* process and reply */
		switch( fromClient.getInt1() ) {
			case Message.CARRY_IT_TO_APPROPRIATE_STORAGE:
				result = baggagePickup.carryItToAppropriateStore(fromClient.getInt2());
				fromServer = new Message(Message.BOOL, result);
				break;
			case Message.NO_MORE_BAGS_TO_COLLECT:
				baggagePickup.noMoreBagsToCollect();
				fromServer = new Message(Message.ACK);
				break;
			case Message.TRY_TO_COLLECT_BAG:
				result = baggagePickup.tryToCollectABag(fromClient.getInt2(), fromClient.getInt3());
				fromServer = new Message(Message.BOOL, result);
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
