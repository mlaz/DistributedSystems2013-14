package Driver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import messages.Message;

public class CommDriverBus implements IDriverBus {
	private Socket commSocket;
	private String serverHostName;
	private int	   portNumber;
	
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	public CommDriverBus(String serverHostName, int portNumber) {
		this.serverHostName = serverHostName;
		this.portNumber = portNumber;
		
		try {
			this.commSocket = new Socket(serverHostName, portNumber);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			in = new ObjectInputStream(commSocket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			out = new ObjectOutputStream(commSocket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void waitingForPassengers() throws InterruptedException {
		try {
			out.writeObject(new Message(Message.PRTR, Message.WAIT_FOR_PASSENGERS));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public int parkAndLetPassOff() throws InterruptedException {
		try {
			out.writeObject(new Message(Message.PRTR, Message.PARK_LET_OFF));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			Message m = (Message) in.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return 999999999;
	}
}
