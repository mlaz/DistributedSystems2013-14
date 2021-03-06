package Servers.arrivalTerminal;
import messages.Message;
import Client.ClientCom;
import Servers.ServerInfo;

/**
 * Classe para comunicação remota com o monitor MArrivalTerminal
 * @author miguel
 */
public class ArrivalTerminalGenRepComm implements IArrivalTerminalGenRep {

	private ServerInfo genRepInfo;
	private ServerInfo arrTermInfo;

    /**
     *
     * @param genRepInfo
     * @param arrTermInfo
     */
    public ArrivalTerminalGenRepComm(ServerInfo genRepInfo, ServerInfo arrTermInfo) {
		this.genRepInfo = genRepInfo;
		this.arrTermInfo = arrTermInfo;
	}

    /**
     *
     */
    @Override
	public void setArrivalTerminal() {

		ClientCom con = new ClientCom(genRepInfo.getHostName(), genRepInfo.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT_INT_STR, Message.SET_ARRIVAL_TERMINAL, arrTermInfo.getPortNumber(), arrTermInfo.getHostName());
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		if (inMessage.getType() != Message.ACK) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
	}
}
