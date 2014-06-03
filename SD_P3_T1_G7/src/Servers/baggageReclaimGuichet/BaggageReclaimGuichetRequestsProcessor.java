package Servers.baggageReclaimGuichet;

import Servers.clientsInterfaces.IRequestProcessor;
import messages.Message;

/**
 * Classe Para processameto de mensagens para o monitor MBaggageReclaimGuichet
 * @author miguel
 */
public class BaggageReclaimGuichetRequestsProcessor implements IRequestProcessor {
	private final int[] validTypes = {Message.INT_INT};
	private MBaggageReclaimGuichet reclaimGuichet;

    /**
     *
     * @param reclaimGuichet
     */
    public BaggageReclaimGuichetRequestsProcessor(MBaggageReclaimGuichet reclaimGuichet) {
		this.reclaimGuichet = reclaimGuichet; 
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
			case Message.RECLAIM_BAGS:
				reclaimGuichet.reclaimBags(fromClient.getInt2());
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
