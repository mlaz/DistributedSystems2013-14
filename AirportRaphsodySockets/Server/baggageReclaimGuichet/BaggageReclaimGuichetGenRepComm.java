package Server.baggageReclaimGuichet;

import messages.Message;
import Client.ClientCom;
import Server.ServerInfo;

public class BaggageReclaimGuichetGenRepComm implements IBaggageReclaimGuichetGenRep {

	private ServerInfo genRepInfo;
	private ServerInfo baggageRelaimInfo;
	
	public BaggageReclaimGuichetGenRepComm(ServerInfo genRepInfo, ServerInfo baggageReclaimInfo) {
		this.genRepInfo = genRepInfo;
		this.baggageRelaimInfo = baggageReclaimInfo;
	}

	@Override
	public void setBaggageReclaimGuichet() {
		ClientCom con = new ClientCom(genRepInfo.getHostName(), genRepInfo.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT_STR, Message.SET_BAGGAGE_RECLAIM_GUICHET, baggageRelaimInfo.getPortNumber(), baggageRelaimInfo.getHostName());
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		if (inMessage.getType() != Message.ACK) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
	}

}
