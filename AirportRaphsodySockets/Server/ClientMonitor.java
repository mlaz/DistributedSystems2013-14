package Server;

import Server.clientsInterfaces.IRequestProcessor;
import messages.Message;

public class ClientMonitor extends Thread {
	
	private ServerCom comm;
	private IRequestProcessor reqProcessor;
	
	public ClientMonitor(ServerCom comm, IRequestProcessor reqProcessor) {
        this.comm = comm;
        this.reqProcessor = reqProcessor;
    }

    @Override
    public void run() {
        Message fromClient = null;
        Message toClient = null;

        fromClient = (Message) comm.readObject();
     
        try {
			toClient = reqProcessor.processAndReply(fromClient);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

        comm.writeObject(toClient);
        comm.close();
    }
}
