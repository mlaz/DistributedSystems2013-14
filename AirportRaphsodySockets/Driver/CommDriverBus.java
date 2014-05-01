package Driver;

import Servers.ServerInfo;
import Client.ClientCom;
import messages.Message;

/**
 * Classe CommDriverBus: classe de comunicação entre as threads de condutor do autocarro (TDriver) e o autocarro (MBus) de forma distribuida
 * @author miguel
 */
public class CommDriverBus implements IDriverBus {
	private ServerInfo busInfo;

	private String myDebugName = "DRIVER_BUS";

    /**
     *
     * @param busInfo
     */
    public CommDriverBus( ServerInfo busInfo ) {
		this.busInfo = busInfo;
	}
	
	@Override
	public void waitingForPassengers() throws InterruptedException {
		ClientCom con = new ClientCom(busInfo.getHostName(), busInfo.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT, Message.WAIT_FOR_PASSENGERS);
		
		printMessageSummary(outMessage, con, busInfo, true);
		
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		printMessageSummary(inMessage, con, busInfo, false);
		
		if (inMessage.getType() != Message.ACK) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}		
	}

	@Override
	public int parkAndLetPassOff() throws InterruptedException {
		ClientCom con = new ClientCom(busInfo.getHostName(), busInfo.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT, Message.PARK_LET_OFF);

		printMessageSummary(outMessage, con, busInfo, true);
		
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		printMessageSummary(inMessage, con, busInfo, false);
		
		if (inMessage.getType() != Message.INT) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
		
		return inMessage.getInt1();
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
