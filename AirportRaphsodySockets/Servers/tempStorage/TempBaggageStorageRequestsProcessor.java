package Servers.tempStorage;

import Servers.Bag;
import Servers.clientsInterfaces.IRequestProcessor;
import messages.Message;

/**
 *
 * @author miguel
 */
public class TempBaggageStorageRequestsProcessor implements IRequestProcessor {
	private final int[] validTypes = {Message.INT_INT_BOOL};

	private MTempBaggageStorage tempStorage;

    /**
     *
     * @param tempStorage
     */
    public TempBaggageStorageRequestsProcessor(MTempBaggageStorage tempStorage) {
		this.tempStorage = tempStorage;
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
			case Message.CARRY_IT_TO_APPROPRIATE_STORAGE:
				Bag bag = new Bag(fromClient.getInt2(), fromClient.getBool());
				tempStorage.carryItToAppropriateStore(bag);
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
