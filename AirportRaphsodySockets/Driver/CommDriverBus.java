package Driver;

import Server.ServerInfo;
import Client.ClientCom;
import messages.Message;

public class CommDriverBus implements IDriverBus {
	private ServerInfo busInfo;

	public CommDriverBus( ServerInfo busInfo ) {
		this.busInfo = busInfo;
	}
	
	@Override
	public void waitingForPassengers() throws InterruptedException {
		ClientCom con = new ClientCom(busInfo.getHostName(), busInfo.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT, Message.WAIT_FOR_PASSENGERS);
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		if (inMessage.getType() != Message.ACK) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}		
	}

	@Override
	public int parkAndLetPassOff() throws InterruptedException {
		ClientCom con = new ClientCom(busInfo.getHostName(), busInfo.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT, Message.PARK_LET_OFF);
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		if (inMessage.getType() != Message.INT) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
		
		return inMessage.getInt1();
	}
}
