package Passenger;

import Server.ServerInfo;
import messages.Message;
import Client.ClientCom;

public class CommPassBus implements IPassengerBus{

	private ServerInfo busInfo;
	
	public CommPassBus (ServerInfo busInfo) {
		this.busInfo = busInfo;
	}
	
	@Override
	public boolean enterTheBus(int passNum) throws InterruptedException {
		ClientCom con = new ClientCom(busInfo.getHostName(), busInfo.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT_INT, Message.ENTER_THE_BUS, passNum);
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
	public void leaveTheBus(int passNum) throws InterruptedException {
		ClientCom con = new ClientCom(busInfo.getHostName(), busInfo.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT_INT, Message.LEAVE_THE_BUS, passNum);
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		if (inMessage.getType() != Message.ACK) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
	}

}
