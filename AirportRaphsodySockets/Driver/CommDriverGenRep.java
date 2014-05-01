package Driver;

import messages.Message;
import Client.ClientCom;
import Servers.ServerInfo;

/**
 * Classe CommDriverGenRep: classe de comunicação entre as threads de condutor do autocarro (TDriver) e o repositorio geral de informação (MGenRep) de forma distribuida
 * @author miguel
 */
public class CommDriverGenRep implements IDriverGenRep {
	private ServerInfo genRepInfo;
	
	private String myDebugName = "DRIVER_GENREP";

    /**
     *
     * @param genRepInfo
     */
    public CommDriverGenRep( ServerInfo genRepInfo ) {
		this.genRepInfo = genRepInfo;
	}

    /**
     *
     * @param state
     */
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

		outMessage = new Message(Message.INT_STR, Message.UPDATE_DRIVER_STATE, state.name());
		
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
     * @return
     */
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
		
		printMessageSummary(outMessage, con, genRepInfo, true);
		
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		printMessageSummary(inMessage, con, genRepInfo, false);
		
		if (inMessage.getType() != Message.INT_STR) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
		
		ServerInfo arrTermExitInfo = new ServerInfo(inMessage.getInt1(), inMessage.getString());
		return new CommDriverArrivalTerminalTransferZone( arrTermExitInfo );
	}

    /**
     *
     * @return
     */
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
		
		printMessageSummary(outMessage, con, genRepInfo, true);
		
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		printMessageSummary(inMessage, con, genRepInfo, false);
		
		if (inMessage.getType() != Message.INT_STR) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
		
		ServerInfo busInfo = new ServerInfo(inMessage.getInt1(), inMessage.getString());
		return new CommDriverBus( busInfo );
	}

    /**
     *
     */
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
    public void setDriverAsDead() {
		ClientCom con = new ClientCom(genRepInfo.getHostName(), genRepInfo.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT, Message.SET_DRIVER_AS_DEAD);
		
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
