package Porter;

import Servers.ServerInfo;
import messages.Message;
import Client.ClientCom;

public class CommPorterTempBaggageStorage implements IPorterTempBaggageStorage {
	
	private ServerInfo tempStorageInfo;
	
	private String myDebugName = "PORTER_TEMPSTORAGE";
	
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

		printMessageSummary(outMessage, con, tempStorageInfo, true);
		
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		printMessageSummary(inMessage, con, tempStorageInfo, false);
		
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
