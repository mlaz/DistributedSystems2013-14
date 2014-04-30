package Servers.arrivalTerminal;

import messages.Message;
import Client.ClientCom;
import Servers.ClientMonitor;
import Servers.ServerCom;
import Servers.ServerInfo;

/**
 *
 * @author miguel
 */
public class ServerArrivalTerminal {
	private static int portNumber = 22161;
	private static String hostName;
	private static ServerInfo genRepInfo;

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
		
		if (args.length != 3) {
			System.out.println("Usage: java ServerArrivalTerminal [thisMachineName] [genRepName] [genRepPort]");
			// System.exit(1);
			args = new String[3];
			args[0] = "localhost";
			args[1] = "localhost";
			args[2] = "22160";
		}
		/* obter parametros do problema */
		genRepInfo = new ServerInfo(Integer.parseInt(args[2]), args[1]);
		hostName = args[0];
		
		int numFlights 	  = getNumFlights();
		int numPassengers = getNumPassengers();
		int maxBags		  = getMaxBags();		
		
		IArrivalTerminalGenRep genRep = new ArrivalTerminalGenRepComm(genRepInfo, new ServerInfo(portNumber, hostName));
		
		/* establecer o serviço */
		genRep.setArrivalTerminal();
		MArrivalTerminal arrivalTerminal = new MArrivalTerminal(numFlights, numPassengers, maxBags);
		ServerCom server = new ServerCom(portNumber);
        server.start();
        ArrivalTerminalRequestsProcessor reqProcessor = new ArrivalTerminalRequestsProcessor(arrivalTerminal);
        
        System.out.println("Arrival Terminal service is listening on port " + portNumber + "...");
        
		/* iniciar processamento de serviçoes */
        ServerCom comm;
        ClientMonitor client;
		while (true) {
			comm = server.accept();
			comm.commSocket.toString();
			client = new ClientMonitor(comm, reqProcessor);
			client.start();
		}
	}
	
	private static int getNumFlights() {

        ClientCom con = new ClientCom(genRepInfo.getHostName(), genRepInfo.getPortNumber());
        Message inMessage, outMessage;

        while (!con.open()) {
            try {
                Thread.sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }

        outMessage = new Message(Message.INT, Message.GET_NUM_FLIGHTS);
        con.writeObject(outMessage);
        inMessage = (Message) con.readObject();
        con.close();

        if (inMessage.getType() != Message.INT) {
            System.out.println("Invalid message type!");
            System.exit(1);
        }

        return inMessage.getInt1();
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
	
	private static int getMaxBags() {

		ClientCom con = new ClientCom(genRepInfo.getHostName(), genRepInfo.getPortNumber());
        Message inMessage, outMessage;

        while (!con.open()) {
            try {
                Thread.sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }

        outMessage = new Message(Message.INT, Message.GET_MAX_BAGS);
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