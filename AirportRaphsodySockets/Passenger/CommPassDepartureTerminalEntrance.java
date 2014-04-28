package Passenger;

import messages.Message;
import Client.ClientCom;
import Server.ServerInfo;

public class CommPassDepartureTerminalEntrance implements IPassengerDepartureTerminalEntrance {
	private ServerInfo deptTermInfo;
	
	public CommPassDepartureTerminalEntrance( ServerInfo deptTermInfo ) {
		this.deptTermInfo = deptTermInfo;
	}
	@Override
	public void prepareNextLeg() throws InterruptedException {
		ClientCom con = new ClientCom(deptTermInfo.getHostName(), deptTermInfo.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT, Message.PREPARE_NEXT_LEG);
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		if (inMessage.getType() != Message.ACK) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
	}

}
