package Porter;

import Server.ServerInfo;
import messages.Message;
import Client.ClientCom;

public class CommPorterBaggagePickupZone implements IPorterBaggagePickupZone {
	private ServerInfo baggagePickupInfo;
	
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
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

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
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		if (inMessage.getType() != Message.ACK) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
	}

}
