package Porter;

import Server.ServerInfo;
import messages.Message;
import Client.ClientCom;

public class CommPorterTempBaggageStorage implements IPorterTempBaggageStorage {
	
	private ServerInfo tempStorageInfo;
	
	public CommPorterTempBaggageStorage(ServerInfo tempStorageInfo) {
		this.tempStorageInfo = tempStorageInfo;
	}
	
	@Override
	public void carryItToAppropriateStore(Bag currentBag) {
		ClientCom con = new ClientCom(tempStorageInfo.getHostName(), tempStorageInfo.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT_INT_BOOL, Message.CARRY_IT_TO_APPROPRIATE_STORAGE, currentBag.getPassNumber(), currentBag.isInTransit());
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		if (inMessage.getType() != Message.INT) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
	}

}
