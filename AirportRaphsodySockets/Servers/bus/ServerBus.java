package Servers.bus;

import messages.Message;
import Client.ClientCom;
import Servers.ClientMonitor;
import Servers.ServerCom;
import Servers.ServerInfo;

public class ServerBus {
	private static int portNumber = 10005; //TODO change to right port
	private static String hostName;
	private static ServerInfo genRepInfo;
	
	public static void main(String[] args) {
		
		if (args.length != 3) {
			System.out.println("Usage: java ServerBus [thisMachineName] [genRepName] [genRepPort]");
			// System.exit(1);
			args = new String[3];
			args[0] = "localhost";
			args[1] = "localhost";
			args[2] = "10000";
		}
		/* obter parametros do problema */
		hostName = args[0];
		genRepInfo = new ServerInfo(Integer.parseInt(args[2]), args[1]);
		
		int numPassengers = getBusDepartureInterval();
		int numSeats	  = getNumSeats();


		IBusGenRep genRep = new BusGenRepComm(genRepInfo, new ServerInfo(portNumber, hostName));
		
		/* establecer o serviço */
		genRep.setBus();
		MBus bus = new MBus(numSeats, numPassengers, genRep);
		ServerCom server = new ServerCom(portNumber);
        server.start();
        BusRequestsProcessor reqProcessor = new BusRequestsProcessor(bus);
        
        System.out.println("Bus service is listening on port " + portNumber + "...");
        
		/* iniciar processamento de serviçoes */
        ServerCom comm;
        ClientMonitor client;
		while (true) {
			comm = server.accept();
			client = new ClientMonitor(comm, reqProcessor);
			client.start();
		}
	}
	
	private static int getBusDepartureInterval() {

        ClientCom con = new ClientCom(genRepInfo.getHostName(), genRepInfo.getPortNumber());
        Message inMessage, outMessage;

        while (!con.open()) {
            try {
                Thread.sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }

        outMessage = new Message(Message.INT, Message.GET_BUS_DEPARTURE_INTERVAL);
        con.writeObject(outMessage);
        inMessage = (Message) con.readObject();
        con.close();

        if (inMessage.getType() != Message.INT) {
            System.out.println("Invalid message type!");
            System.exit(1);
        }

        return inMessage.getInt1();
    }
	
	
	private static int getNumSeats() {

		ClientCom con = new ClientCom(genRepInfo.getHostName(), genRepInfo.getPortNumber());
        Message inMessage, outMessage;

        while (!con.open()) {
            try {
                Thread.sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }

        outMessage = new Message(Message.INT, Message.GET_NUM_SEATS);
        con.writeObject(outMessage);
        inMessage = (Message) con.readObject();
        con.close();

        if (inMessage.getType() != Message.INT) {
            System.out.println("Invalid message type!");
            System.exit(1);
        }

        return inMessage.getInt1();
    }
}