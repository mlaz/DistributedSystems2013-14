package Servers.bus;

import messages.Message;
import Client.ClientCom;
import Servers.ServerInfo;

public class BusGenRepComm implements IBusGenRep {
	private ServerInfo genRepInfo;
	private ServerInfo busInfo;
	
	public BusGenRepComm(ServerInfo genRepInfo, ServerInfo busInfo) {
		this.genRepInfo = genRepInfo;
		this.busInfo = busInfo;
	}

	@Override
	public void setBus() {
		ClientCom con = new ClientCom(genRepInfo.getHostName(), genRepInfo.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT_INT_STR, Message.SET_BUS, busInfo.getPortNumber(), busInfo.getHostName());
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		if (inMessage.getType() != Message.ACK) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
	}

	@Override
	public void updateDriverSeats(int[] seats) {
		ClientCom con = new ClientCom(genRepInfo.getHostName(), genRepInfo.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}
		
		outMessage = new Message(Message.INT_INTARR, Message.UPDATE_DRIVER_SEATS, seats);
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		if (inMessage.getType() != Message.ACK) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
	}

}
