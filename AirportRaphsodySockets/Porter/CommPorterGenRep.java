package Porter;

import messages.Message;
import Client.ClientCom;
import Servers.ServerInfo;

/**
 *
 * @author miguel
 */
public class CommPorterGenRep implements IPorterGenRep {
	private ServerInfo genRepInfo;
	
	private String myDebugName = "PORTER_GENREP";

    /**
     *
     * @param genRepInfo
     */
    public CommPorterGenRep( ServerInfo genRepInfo ) {
		this.genRepInfo = genRepInfo;
	}

    /**
     *
     * @return
     */
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
		
		printMessageSummary(outMessage, con, genRepInfo, true);
		
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		printMessageSummary(inMessage, con, genRepInfo, false);
		
		if (inMessage.getType() != Message.INT_STR) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
		ServerInfo arrTermInfo = new ServerInfo(inMessage.getInt1(), inMessage.getString());
		return new CommPorterArrivalTerminal( arrTermInfo );
	}

    /**
     *
     * @return
     */
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
		
		printMessageSummary(outMessage, con, genRepInfo, true);
		
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		printMessageSummary(inMessage, con, genRepInfo, false);
		
		if (inMessage.getType() != Message.INT_STR) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
		ServerInfo baggagePickupInfo = new ServerInfo(inMessage.getInt1(), inMessage.getString());
		return new CommPorterBaggagePickupZone( baggagePickupInfo );
	}

    /**
     *
     * @return
     */
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
		
		printMessageSummary(outMessage, con, genRepInfo, true);
		
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		printMessageSummary(inMessage, con, genRepInfo, false);
		
		if (inMessage.getType() != Message.INT_STR) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
		ServerInfo tempStorageInfo = new ServerInfo(inMessage.getInt1(), inMessage.getString());
		return new CommPorterTempBaggageStorage( tempStorageInfo );
	}

    /**
     *
     */
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
		
		printMessageSummary(outMessage, con, genRepInfo, true);
		
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		printMessageSummary(inMessage, con, genRepInfo, false);
		
		if (inMessage.getType() != Message.ACK) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
	}

    /**
     *
     */
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

		printMessageSummary(outMessage, con, genRepInfo, true);
		
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		printMessageSummary(inMessage, con, genRepInfo, false);
		
		if (inMessage.getType() != Message.ACK) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}	
	}

    /**
     *
     */
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
		
		printMessageSummary(outMessage, con, genRepInfo, true);
		
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		printMessageSummary(inMessage, con, genRepInfo, false);
		
		if (inMessage.getType() != Message.ACK) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
		
	}

    /**
     *
     */
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
		
		printMessageSummary(outMessage, con, genRepInfo, true);
		
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		printMessageSummary(inMessage, con, genRepInfo, false);
		
		if (inMessage.getType() != Message.ACK) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
	}

    /**
     *
     * @param state
     */
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
		
		printMessageSummary(outMessage, con, genRepInfo, true);
		
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		printMessageSummary(inMessage, con, genRepInfo, false);
		
		if (inMessage.getType() != Message.ACK) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
	}

    /**
     *
     */
    public void setPorterAsDead() {
		ClientCom con = new ClientCom(genRepInfo.getHostName(), genRepInfo.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT, Message.SET_PORTER_AS_DEAD);
		
		printMessageSummary(outMessage, con, genRepInfo, true);
		
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		printMessageSummary(inMessage, con, genRepInfo, false);
		
		if (inMessage.getType() != Message.ACK) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
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
