package Servers.departureTerminalEntrance;

import messages.Message;
import Client.ClientCom;
import Servers.ClientMonitor;
import Servers.ServerCom;
import Servers.ServerInfo;

public class ServerDepartureTerminalEntrance {
	private static int portNumber = 22166;
	private static String hostName;
	private static ServerInfo genRepInfo;
	
	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println("Usage: java ServerDepartureTerminalEntrance [thisMachineName] [genRepName] [genRepPort]");
			// System.exit(1);
			args = new String[3];
			args[0] = "localhost";
			args[1] = "localhost";
			args[2] = "22160";
		}
		
		/* obter parametros do problema */
		hostName = args[0];
		genRepInfo = new ServerInfo(Integer.parseInt(args[2]), args[1]);
		
		int numPassengers = getNumPassengers();
		IDepartureTerminalEntranceGenRep genRep = new DepartureTerminalEntranceGenRepComm(genRepInfo, new ServerInfo(portNumber, hostName));
		
		/* establecer o serviço */
		genRep.setDepartureTerminalEntrance();
		MDepartureTerminalEntrance departEntrance = new MDepartureTerminalEntrance(numPassengers);
		ServerCom server = new ServerCom(portNumber);
        server.start();
        DepartureTerminalEntranceRequestsProcessor reqProcessor = new DepartureTerminalEntranceRequestsProcessor(departEntrance);
        
        System.out.println("Departure Terminal Entrance service is listening on port " + portNumber + "...");
        
		/* iniciar processamento de serviçoes */
        ServerCom comm;
        ClientMonitor client;
		while (true) {
			comm = server.accept();
			client = new ClientMonitor(comm, reqProcessor);
			client.start();
		}
	}
	
	private static int getNumPassengers() {

        ClientCom con = new ClientCom(genRepInfo.getHostName(), genRepInfo.getPortNumber());
        Message inMessage, outMessage;

        while (!con.open()) {
            try {
                Thread.sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }

        outMessage = new Message(Message.INT, Message.GET_NUM_PASSENGERS);
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