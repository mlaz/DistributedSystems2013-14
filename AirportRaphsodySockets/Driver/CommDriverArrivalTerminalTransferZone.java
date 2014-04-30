package Driver;

import Servers.ServerInfo;
import messages.Message;
import Client.ClientCom;

public class CommDriverArrivalTerminalTransferZone implements IDriverArrivalTerminalTransferZone {
	private ServerInfo arrTermExit;
	
	private String myDebugName = "DRIVER_ARR_TERM_EXIT";
	
	public CommDriverArrivalTerminalTransferZone( ServerInfo arrTermExit ) {
		this.arrTermExit = arrTermExit;
	}
	
	@Override
	public void announcingDeparture() {
		ClientCom con = new ClientCom(arrTermExit.getHostName(), arrTermExit.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT, Message.ANNOUNCING_BUS_DEPARTURE);

		printMessageSummary(outMessage, con, arrTermExit, true);
		
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		printMessageSummary(inMessage, con, arrTermExit, false);
		
		if (inMessage.getType() != Message.ACK) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
	}

	@Override
	public boolean announcingBusBoaring(int lastPassengers) {
		ClientCom con = new ClientCom(arrTermExit.getHostName(), arrTermExit.getPortNumber());
		Message inMessage, outMessage;
	
		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}
	
		outMessage = new Message(Message.INT_INT, Message.ANNOUNCING_BUS_BOARDING, lastPassengers);
	
		printMessageSummary(outMessage, con, arrTermExit, true);
		
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();
	
		printMessageSummary(inMessage, con, arrTermExit, false);
		
		if (inMessage.getType() != Message.BOOL) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
		
		return inMessage.getBool();
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
