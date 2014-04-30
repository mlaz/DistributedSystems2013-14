package Passenger;

import messages.Message;
import Client.ClientCom;
import Servers.ServerInfo;

public class CommPassBaggageCollectionPoint implements IPassengerBaggageCollectionPoint {
	private ServerInfo baggagePickup;
	
	private String myDebugName = "PASS_PICKUP";
	
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
		
		printMessageSummary(outMessage, con, baggagePickup, true);
		
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		printMessageSummary(inMessage, con, baggagePickup, false);
		
		if (inMessage.getType() != Message.BOOL) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
		
		return inMessage.getBool();
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
