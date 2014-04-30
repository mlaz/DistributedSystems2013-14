package Porter;

import Servers.ServerInfo;
import messages.Message;
import Client.ClientCom;

public class CommPorterArrivalTerminal implements IPorterArrivalTerminal {
	private ServerInfo arrTermInfo;
	
	private String myDebugName = "PORTER_ARR_TERM";
	
	public CommPorterArrivalTerminal( ServerInfo arrTermInfo ) {
		this.arrTermInfo = arrTermInfo;
	}
	
	@Override
	public boolean takeARest() throws InterruptedException {
		ClientCom con = new ClientCom(arrTermInfo.getHostName(), arrTermInfo.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT, Message.TAKE_A_REST);
		
		printMessageSummary(outMessage, con, arrTermInfo, true);
		
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		printMessageSummary(inMessage, con, arrTermInfo, false);
		
		if (inMessage.getType() != Message.BOOL) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
		
		return inMessage.getBool();
	}

	@Override
	public Bag tryToCollectABag() {
		ClientCom con = new ClientCom(arrTermInfo.getHostName(), arrTermInfo.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT, Message.TRY_TO_COLLECT_BAG);
		
		printMessageSummary(outMessage, con, arrTermInfo, true);
		
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		printMessageSummary(inMessage, con, arrTermInfo, false);
		
		if (inMessage.getType() != Message.INT_BOOL) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
		if( inMessage.getInt1() == -1 ) {
			return null;
		} else {
			return new Bag(inMessage.getInt1(), inMessage.getBool());
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
