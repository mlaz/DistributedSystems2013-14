package Servers.arrivalTerminalExit;

import messages.Message;
import Client.ClientCom;
import Servers.ClientMonitor;
import Servers.ServerCom;
import Servers.ServerInfo;

/**
 * Classe de servidor com replicação para receção de pedidos ao monior por parte das threads(clientes)
 * @author miguel
 */
public class ServerArrivalTerminalExit {

	private static int portNumber = 22162;
	private static String hostName;
	private static ServerInfo genRepInfo;

    /**
     *
     * @param args
     */
    public static void main(String[] args) {

		if (args.length != 3) {
			System.out.println("Usage: java ServerArrivalTerminalExit [thisMachineName] [genRepName] [genRepPort]");
			// System.exit(1);
			args = new String[3];
			args[0] = "localhost";
			args[1] = "localhost";
			args[2] = "22160";
		}

		/* obter parametros do problema */
		hostName = args[0];
		genRepInfo = new ServerInfo(Integer.parseInt(args[2]), args[1]);

		int numFlights = getNumFlights();
		int numPassengers = getNumPassengers();
		int numSeats = getNumSeats();
		
		IArrivalTerminalExitGenRep genRep = new ArrivalTerminalExitGenRepComm(genRepInfo, new ServerInfo(portNumber, hostName));

		/* establecer o serviço */
		genRep.setArrivalTerminalExit();
		MArrivalTerminalExit arrTermExit = new MArrivalTerminalExit(numFlights, numPassengers, numSeats, genRep);
		ServerCom server = new ServerCom(portNumber);
		server.start();
		ArrivalTerminalExitRequestsProcessor reqProcessor = new ArrivalTerminalExitRequestsProcessor(arrTermExit);

		System.out.println("Arrival Terminal Exit service is listening on port " + portNumber + "...");

		/* iniciar processamento de serviçoes */
		ServerCom comm;
		ClientMonitor client;
		while (true) {
			comm = server.accept();
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