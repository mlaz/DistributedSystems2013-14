package Driver;

import messages.Message;
import Client.ClientCom;
import Server.ServerInfo;

public class CommDriverGenRep implements IDriverGenRep {
	private ServerInfo genRepInfo;
	
	
	public CommDriverGenRep( ServerInfo genRepInfo ) {
		this.genRepInfo = genRepInfo;
	}
	
	@Override
	public void updateDriverState(EDriverStates state) {
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

	@Override
	public IDriverArrivalTerminalTransferZone getArrivalTerminalExit() {
		ClientCom con = new ClientCom(genRepInfo.getHostName(), genRepInfo.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT, Message.GET_ARRIVAL_TERMINAL_EXIT);
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		if (inMessage.getType() != Message.INT_STR) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
		
		ServerInfo arrTermExitInfo = new ServerInfo(inMessage.getInt1(), inMessage.getString());
		return new CommDriverArrivalTerminalTransferZone( arrTermExitInfo );
	}

	@Override
	public IDriverBus getBus() {
		ClientCom con = new ClientCom(genRepInfo.getHostName(), genRepInfo.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT, Message.GET_BUS);
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		if (inMessage.getType() != Message.INT_STR) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
		
		ServerInfo busInfo = new ServerInfo(inMessage.getInt1(), inMessage.getString());
		return new CommDriverBus( busInfo );
	}

	@Override
	public void registerDriver() {
		ClientCom con = new ClientCom(genRepInfo.getHostName(), genRepInfo.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT, Message.REGISTER_DRIVER);
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		if (inMessage.getType() != Message.ACK) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
	}

}
