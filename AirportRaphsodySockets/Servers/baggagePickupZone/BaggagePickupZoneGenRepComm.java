package Servers.baggagePickupZone;

import messages.Message;
import Client.ClientCom;
import Servers.ServerInfo;

public class BaggagePickupZoneGenRepComm implements IBaggagePickupZoneGenRep {

	private ServerInfo genRepInfo;
	private ServerInfo baggagePickupInfo;
	
	public BaggagePickupZoneGenRepComm(ServerInfo genRepInfo, ServerInfo baggagePickupInfo) {
		this.genRepInfo = genRepInfo;
		this.baggagePickupInfo = baggagePickupInfo;
	}

	@Override
	public void setBaggagePickupZone() {
		ClientCom con = new ClientCom(genRepInfo.getHostName(), genRepInfo.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT_INT_STR, Message.SET_BAGGAGE_PICKUP_ZONE, baggagePickupInfo.getPortNumber(), baggagePickupInfo.getHostName());
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		if (inMessage.getType() != Message.ACK) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
	}

}
