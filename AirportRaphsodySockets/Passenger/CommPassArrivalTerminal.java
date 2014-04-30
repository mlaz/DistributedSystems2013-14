package Passenger;

import Servers.ServerInfo;
import messages.Message;
import Client.ClientCom;

/**
 *
 * @author miguel
 */
public class CommPassArrivalTerminal implements IPassengerArrivalTerminal {
	private ServerInfo arrTermInfo;
	
	private String myDebugName = "PASS_ARR_TERM";

    /**
     *
     * @param arrTermInfo
     */
    public CommPassArrivalTerminal( ServerInfo arrTermInfo ) {
		this.arrTermInfo = arrTermInfo;
	}
	
	@Override
	public void whatSouldIDo(int passengerId) throws InterruptedException {
		ClientCom con = new ClientCom(arrTermInfo.getHostName(), arrTermInfo.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT_INT, Message.WHAT_SHOULD_I_DO, passengerId);
		printMessageSummary(outMessage, con, arrTermInfo, true);
		
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		printMessageSummary(inMessage, con, arrTermInfo, false);
		
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
