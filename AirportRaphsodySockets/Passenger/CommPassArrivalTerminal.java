package Passenger;

import Server.ServerInfo;
import messages.Message;
import Client.ClientCom;

public class CommPassArrivalTerminal implements IPassengerArrivalTerminal {
	private ServerInfo arrTermInfo;
	
	public CommPassArrivalTerminal( ServerInfo arrTermInfo ) {
		this.arrTermInfo = arrTermInfo;
	}
	
	@Override
	public void whatSouldIDo(int passengerId) throws InterruptedException {
		ClientCom con = new ClientCom(arrTermInfo.getHostName(), arrTermInfo.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT_INT, Message.WHAT_SHOULD_I_DO, passengerId);
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		if (inMessage.getType() != Message.ACK) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
	}

}
