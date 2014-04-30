package Passenger;

import Servers.ServerInfo;
import messages.Message;
import Client.ClientCom;

/**
 *
 * @author miguel
 */
public class CommPassBus implements IPassengerBus{

	private ServerInfo busInfo;
	
	private String myDebugName;

    /**
     *
     * @param busInfo
     */
    public CommPassBus (ServerInfo busInfo) {
		this.busInfo = busInfo;
	}
	
	@Override
	public boolean enterTheBus(int passNum) throws InterruptedException {
		ClientCom con = new ClientCom(busInfo.getHostName(), busInfo.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT_INT, Message.ENTER_THE_BUS, passNum);
		
		printMessageSummary(outMessage, con, busInfo, true);
		
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		printMessageSummary(inMessage, con, busInfo, false);
		
		if (inMessage.getType() != Message.BOOL) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
		
		return inMessage.getBool();
	}

	@Override
	public void leaveTheBus(int passNum) throws InterruptedException {
		ClientCom con = new ClientCom(busInfo.getHostName(), busInfo.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT_INT, Message.LEAVE_THE_BUS, passNum);
		con.writeObject(outMessage);
		
		printMessageSummary(outMessage, con, busInfo, true);
		
		inMessage = (Message) con.readObject();
		con.close();

		printMessageSummary(inMessage, con, busInfo, false);
		
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
