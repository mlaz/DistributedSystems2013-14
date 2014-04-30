package Servers.arrivalTerminalExit;

import messages.Message;
import Client.ClientCom;
import Servers.ServerInfo;

/**
 *
 * @author miguel
 */
public class ArrivalTerminalExitGenRepComm implements IArrivalTerminalExitGenRep {
	private ServerInfo genRepInfo;
	private ServerInfo arrTermExitInfo;

    /**
     *
     * @param genRepInfo
     * @param serverInfo
     */
    public ArrivalTerminalExitGenRepComm(ServerInfo genRepInfo, ServerInfo serverInfo) {
		this.genRepInfo = genRepInfo;
		this.arrTermExitInfo = serverInfo;
	}

    /**
     *
     */
    @Override
	public void setArrivalTerminalExit() {
		ClientCom con = new ClientCom(genRepInfo.getHostName(), genRepInfo.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT_INT_STR, Message.SET_ARRIVAL_TERMINAL_EXIT, arrTermExitInfo.getPortNumber(), arrTermExitInfo.getHostName());
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		if (inMessage.getType() != Message.ACK) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
	}

    /**
     *
     * @param busQueue
     */
    @Override
	public void updateDriverQueue(int[] busQueue) {

		ClientCom con = new ClientCom(genRepInfo.getHostName(), genRepInfo.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}
		
		outMessage = new Message(Message.INT_INTARR, Message.UPDATE_DRIVER_QUEUE, busQueue);
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		if (inMessage.getType() != Message.ACK) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
	}

}
