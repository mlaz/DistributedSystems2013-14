package Driver;

import Server.ServerInfo;
import messages.Message;
import Client.ClientCom;

public class CommDriverArrivalTerminalTransferZone implements IDriverArrivalTerminalTransferZone {
	private ServerInfo arrTermExit;
	
	public CommDriverArrivalTerminalTransferZone( ServerInfo arrTermExit ) {
		this.arrTermExit = arrTermExit;
	}
	
	@Override
	public void announcingDeparture() {
		ClientCom con = new ClientCom(arrTermExit.getHostName(), arrTermExit.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT, Message.ANNOUNCING_BUS_DEPARTURE);
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		if (inMessage.getType() != Message.ACK) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
	}

	@Override
	public boolean announcingBusBoaring(int lastPassengers) {
		ClientCom con = new ClientCom(arrTermExit.getHostName(), arrTermExit.getPortNumber());
		Message inMessage, outMessage;
	
		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}
	
		outMessage = new Message(Message.INT_INT, Message.ANNOUNCING_BUS_BOARDING, lastPassengers);
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();
	
		if (inMessage.getType() != Message.BOOL) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
		
		return inMessage.getBool();
	}

}
