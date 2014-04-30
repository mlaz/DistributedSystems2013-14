package Porter;

import Servers.ServerInfo;
import messages.Message;
import Client.ClientCom;

/**
 *
 * @author miguel
 */
public class CommPorterBaggagePickupZone implements IPorterBaggagePickupZone {
	private ServerInfo baggagePickupInfo;
	
	private String myDebugName = "PORTER_PICKUP";

    /**
     *
     * @param baggagePickupInfo
     */
    public CommPorterBaggagePickupZone( ServerInfo baggagePickupInfo ) {
		this.baggagePickupInfo = baggagePickupInfo;
	}
	
	@Override
	public boolean carryItToAppropriateStore(int passId) {
		ClientCom con = new ClientCom(baggagePickupInfo.getHostName(), baggagePickupInfo.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT_INT, Message.CARRY_IT_TO_APPROPRIATE_STORAGE,passId);
		
		printMessageSummary(outMessage, con, baggagePickupInfo, true);
		
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		printMessageSummary(inMessage, con, baggagePickupInfo, false);
		
		if (inMessage.getType() != Message.BOOL) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
		
		return inMessage.getBool();
	}

	@Override
	public void noMoreBagsToCollect() throws InterruptedException {
		ClientCom con = new ClientCom(baggagePickupInfo.getHostName(), baggagePickupInfo.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT, Message.NO_MORE_BAGS_TO_COLLECT);
		
		printMessageSummary(outMessage, con, baggagePickupInfo, true);
		
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		printMessageSummary(inMessage, con, baggagePickupInfo, false);
		
		if (inMessage.getType() != Message.ACK) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
	}

	
	private void printMessageSummary(Message m, ClientCom con, ServerInfo id, boolean outMessage) {
		if( outMessage ) {
			System.out.println(myDebugName+" ("+con.commSocket.getLocalPort()+") sending message to " + id.getHostName() + ":"+id.getPortNumber());
		} else {
			System.out.println(myDebugName+" ("+con.commSocket.getLocalPort()+") received message from " + id.getHostName() + ":"+id.getPortNumber());
		}
		m.print();
	}
}
