package Passenger;

import messages.Message;
import Client.ClientCom;
import Servers.ServerInfo;

/**
 *
 * @author miguel
 */
public class CommPassDepartureTerminalEntrance implements IPassengerDepartureTerminalEntrance {
	private ServerInfo deptTermInfo;
	
	private String myDebugName = "PASS_DEPARTURE";

    /**
     *
     * @param deptTermInfo
     */
    public CommPassDepartureTerminalEntrance( ServerInfo deptTermInfo ) {
		this.deptTermInfo = deptTermInfo;
	}

    /**
     *
     * @throws InterruptedException
     */
    @Override
	public void prepareNextLeg() throws InterruptedException {
		ClientCom con = new ClientCom(deptTermInfo.getHostName(), deptTermInfo.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT, Message.PREPARE_NEXT_LEG);
		
		printMessageSummary(outMessage, con, deptTermInfo, true);
		
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		printMessageSummary(inMessage, con, deptTermInfo, false);
		
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
