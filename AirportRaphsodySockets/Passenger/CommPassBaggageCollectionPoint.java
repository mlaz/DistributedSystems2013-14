package Passenger;

import messages.Message;
import Client.ClientCom;
import Server.ServerInfo;

public class CommPassBaggageCollectionPoint implements IPassengerBaggageCollectionPoint {
	private ServerInfo baggagePickup;
	
	public CommPassBaggageCollectionPoint( ServerInfo baggagePickup ) {
		this.baggagePickup = baggagePickup;
	}
	
	@Override
	public boolean tryToCollectABag(int passengerNumber, int flightNum) throws InterruptedException {
		ClientCom con = new ClientCom(baggagePickup.getHostName(), baggagePickup.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT_INT_INT, Message.TRY_TO_COLLECT_BAG, passengerNumber, flightNum);
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
