package Passenger;

import messages.Message;
import Client.ClientCom;
import Server.ServerInfo;

public class CommPassArrivalExitTransferZone implements IPassengerArrivalExitTransferZone {
	private ServerInfo arrTermExitInfo;
	
	public CommPassArrivalExitTransferZone( ServerInfo arrTermExitInfo ) {
		this.arrTermExitInfo = arrTermExitInfo;
	}
	
	@Override
	public void takeABus(int passNumber) throws InterruptedException {
		ClientCom con = new ClientCom(arrTermExitInfo.getHostName(), arrTermExitInfo.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT_INT, Message.TAKE_A_BUS, passNumber);
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		if (inMessage.getType() != Message.ACK) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
	}

	@Override
	public void goHome(int passNumber) {
		ClientCom con = new ClientCom(arrTermExitInfo.getHostName(), arrTermExitInfo.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT_INT, Message.GO_HOME, passNumber);
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		if (inMessage.getType() != Message.ACK) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
	}

}
