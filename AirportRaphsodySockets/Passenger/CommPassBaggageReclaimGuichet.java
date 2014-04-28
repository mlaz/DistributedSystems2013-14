package Passenger;

import messages.Message;
import Client.ClientCom;
import Server.ServerInfo;

public class CommPassBaggageReclaimGuichet implements IPassengerBaggageReclaimGuichet {
	private ServerInfo reclaimGuichetInfo;
	
	public CommPassBaggageReclaimGuichet( ServerInfo reclaimGuichetInfo ) {
		this.reclaimGuichetInfo = reclaimGuichetInfo;
	}
	@Override
	public void reclaimBags(int passengerNumber) {
		ClientCom con = new ClientCom(reclaimGuichetInfo.getHostName(), reclaimGuichetInfo.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT_INT, Message.RECLAIM_BAGS, passengerNumber);
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		if (inMessage.getType() != Message.ACK) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
	}

}
