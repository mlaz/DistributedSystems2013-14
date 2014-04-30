package Servers.tempStorage;

import messages.Message;
import Client.ClientCom;
import Servers.ServerInfo;

public class TempBaggageStorageGenRepComm implements ITempBaggageStorageGenRep {
	private ServerInfo genRepInfo;
	private ServerInfo tempBaggageInfo;
	
	public TempBaggageStorageGenRepComm(ServerInfo genRepInfo, ServerInfo tempBaggageInfo) {
		this.genRepInfo = genRepInfo;
		this.tempBaggageInfo = tempBaggageInfo;
	}

	@Override
	public void setTempStorage() {
		ClientCom con = new ClientCom(genRepInfo.getHostName(), genRepInfo.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT_INT_STR, Message.SET_TEMP_BAGGAGE_STORAGE, tempBaggageInfo.getPortNumber(), tempBaggageInfo.getHostName());
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		if (inMessage.getType() != Message.ACK) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
	}

}
