package Passenger;

import messages.Message;
import Client.ClientCom;
import Servers.ServerInfo;

/**
 * Classe CommDriverGenRep: classe de comunicação entre as threads de passageiro (TPassenger) e a zona de transferencia do terminal de chegada (MArrivalExitTransferZone) de forma distribuida
 * @author miguel
 */
public class CommPassArrivalExitTransferZone implements IPassengerArrivalExitTransferZone {
	private ServerInfo arrTermExitInfo;
	private String myDebugName = "PARR_ARR_TERM_EXIT";

    /**
     *
     * @param arrTermExitInfo
     */
    public CommPassArrivalExitTransferZone( ServerInfo arrTermExitInfo ) {
		this.arrTermExitInfo = arrTermExitInfo;
	}
	
	@Override
	public void takeABus(int passNumber) throws InterruptedException {
		ClientCom con = new ClientCom(arrTermExitInfo.getHostName(), arrTermExitInfo.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT_INT, Message.TAKE_A_BUS, passNumber);
		
		printMessageSummary(outMessage, con, arrTermExitInfo, true);
		
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		printMessageSummary(inMessage, con, arrTermExitInfo, false);
		
		if (inMessage.getType() != Message.ACK) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
	}

    /**
     *
     * @param passNumber
     */
    @Override
	public void goHome(int passNumber) {
		ClientCom con = new ClientCom(arrTermExitInfo.getHostName(), arrTermExitInfo.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT_INT, Message.GO_HOME, passNumber);
		
		printMessageSummary(outMessage, con, arrTermExitInfo, true);
		
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		printMessageSummary(inMessage, con, arrTermExitInfo, false);
		
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
