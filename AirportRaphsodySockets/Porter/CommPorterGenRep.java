package Porter;

import messages.Message;
import Client.ClientCom;
import Server.ServerInfo;

public class CommPorterGenRep implements IPorterGenRep {
	private ServerInfo genRepInfo;
	
	public CommPorterGenRep( ServerInfo genRepInfo ) {
		this.genRepInfo = genRepInfo;
	}
	
	@Override
	public IPorterArrivalTerminal getArrivalTerminal() {
		ClientCom con = new ClientCom(genRepInfo.getHostName(), genRepInfo.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT, Message.GET_ARRIVAL_TERMINAL);
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		if (inMessage.getType() != Message.INT_STR) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
		ServerInfo arrTermInfo = new ServerInfo(inMessage.getInt1(), inMessage.getString());
		return new CommPorterArrivalTerminal( arrTermInfo );
	}

	@Override
	public IPorterBaggagePickupZone getBaggagePickupZone() {
		ClientCom con = new ClientCom(genRepInfo.getHostName(), genRepInfo.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT, Message.GET_BAGGAGE_PICKUP_ZONE);
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		if (inMessage.getType() != Message.INT_STR) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
		ServerInfo baggagePickupInfo = new ServerInfo(inMessage.getInt1(), inMessage.getString());
		return new CommPorterBaggagePickupZone( baggagePickupInfo );
	}

	@Override
	public IPorterTempBaggageStorage getTempBaggageStorage() {
		ClientCom con = new ClientCom(genRepInfo.getHostName(), genRepInfo.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT, Message.GET_TEMP_BAGGAGE_STORAGE);
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		if (inMessage.getType() != Message.INT_STR) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
		ServerInfo tempStorageInfo = new ServerInfo(inMessage.getInt1(), inMessage.getString());
		return new CommPorterTempBaggageStorage( tempStorageInfo );
	}

	@Override
	public void registerPorter() {
		ClientCom con = new ClientCom(genRepInfo.getHostName(), genRepInfo.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT, Message.REGISTER_PORTER);
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		if (inMessage.getType() != Message.ACK) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
	}

	@Override
	public void removeLuggageAtPlane() {
		ClientCom con = new ClientCom(genRepInfo.getHostName(), genRepInfo.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT, Message.REMOVE_LUGGAGE_AT_PLANE);
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		if (inMessage.getType() != Message.ACK) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}	
	}

	@Override
	public void incLuggageAtCB() {
		ClientCom con = new ClientCom(genRepInfo.getHostName(), genRepInfo.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT, Message.INC_LUGGAGE_AT_CB);
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		if (inMessage.getType() != Message.ACK) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
		
	}

	@Override
	public void incLuggageAtSR() {
		ClientCom con = new ClientCom(genRepInfo.getHostName(), genRepInfo.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT, Message.INC_LUGGAGE_AT_SR);
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		if (inMessage.getType() != Message.ACK) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
	}

	@Override
	public void updatePorterState(EPorterStates state) {
		ClientCom con = new ClientCom(genRepInfo.getHostName(), genRepInfo.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT_STR, Message.UPDATE_PORTER_STATE, state.name());
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		if (inMessage.getType() != Message.ACK) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
	}

}
