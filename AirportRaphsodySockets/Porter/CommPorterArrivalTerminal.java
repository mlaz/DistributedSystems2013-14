package Porter;

import Server.ServerInfo;
import messages.Message;
import Client.ClientCom;

public class CommPorterArrivalTerminal implements IPorterArrivalTerminal {
	private ServerInfo arrTermInfo;
	
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
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		if (inMessage.getType() != Message.INT_BOOL) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
		
		return new Bag(inMessage.getInt1(), inMessage.getBool());
	}

}
