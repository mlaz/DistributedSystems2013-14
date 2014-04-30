package Passenger;

import messages.Message;
import Client.ClientCom;
import Servers.ServerInfo;

public class ClientPassengers {
	private static ServerInfo genRepInfo;
	
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Usage: java ClientPassengers [genRepName] [genRepPort]");
			// System.exit(1);
			args = new String[2];
			args[0] = "localhost";
			args[1] = "22160";
		}
		
		genRepInfo = new ServerInfo( Integer.parseInt(args[1]), args[0] );
		IPassengerGenRep genRep  = new CommPassGenRep( genRepInfo );
		
        int K = getNumFlights();
        int N = getNumPassengers();
        int M = getMaxBags();

		TPassenger[][] passengerList =  new TPassenger[K][N];        
		int flightNumber;
		int passNumber;
		int nbags = M;
		Boolean transit = true;
		for(flightNumber = 0; flightNumber < K; flightNumber++){
		//generating passengers         
		        for (passNumber = 0; passNumber < N; passNumber++) {
		                passengerList[flightNumber][passNumber] = new TPassenger(passNumber, nbags, transit, flightNumber, genRep);
		        
		                transit = !transit;
		                nbags = (nbags == M) ? 0 : nbags + 1;
		        }
		        
		        System.out.println("flight# " + flightNumber + " NPASSENGERS:" + N);
		        //starting threads
		        System.out.println("NEW airplane-----------------------------------------\n");
		        
		        for (passNumber = 0; passNumber < N; passNumber++) 
		                passengerList[flightNumber][passNumber].start();
		                
		                
		        for (passNumber = 0; passNumber < N; passNumber++)
		                try {
		                        passengerList[flightNumber][passNumber].join();
		                } catch (InterruptedException e) {
		                        // TODO Auto-generated catch block
		                        e.printStackTrace();
		                }
		        System.out.println("ALL Passengers Done-----------------------------------------\n");
		}
		
		// WAIT FOR PORTER AND DRIVER TO DIE
		System.out.print("Waiting for porter to die... ");
		waitForPorterToDie();
		System.out.println(" Dead!");
		System.out.print("Waiting for driver to die... ");
		waitForDriverToDie();
		System.out.println(" Dead!");
		
        System.out.println("ALL Threads Done-----------------------------------------\n");
        endSimulation();
        System.exit(0);
	}
	
	private static void waitForPorterToDie() {
		ClientCom con = new ClientCom(genRepInfo.getHostName(), genRepInfo.getPortNumber());
	    Message inMessage, outMessage;

	    while (!con.open()) {
	        try {
	            Thread.sleep((long) (10));
	        } catch (InterruptedException e) {
	        }
	    }

	    outMessage = new Message(Message.INT, Message.WAIT_FOR_PORTER_TO_DIE);
	    con.writeObject(outMessage);
	    inMessage = (Message) con.readObject();
	    con.close();

	    if (inMessage.getType() != Message.ACK) {
	        System.out.println("Invalid message type!");
	        System.exit(1);
	    }
	}
	
	private static void waitForDriverToDie() {
		ClientCom con = new ClientCom(genRepInfo.getHostName(), genRepInfo.getPortNumber());
	    Message inMessage, outMessage;

	    while (!con.open()) {
	        try {
	            Thread.sleep((long) (10));
	        } catch (InterruptedException e) {
	        }
	    }

	    outMessage = new Message(Message.INT, Message.WAIT_FOR_DRIVER_TO_DIE);
	    con.writeObject(outMessage);
	    inMessage = (Message) con.readObject();
	    con.close();

	    if (inMessage.getType() != Message.ACK) {
	        System.out.println("Invalid message type!");
	        System.exit(1);
	    }
	}
	
	private static void endSimulation() {

	    ClientCom con = new ClientCom(genRepInfo.getHostName(), genRepInfo.getPortNumber());
	    Message inMessage, outMessage;

	    while (!con.open()) {
	        try {
	            Thread.sleep((long) (10));
	        } catch (InterruptedException e) {
	        }
	    }

	    outMessage = new Message(Message.INT, Message.END_SIMULATION);
	    con.writeObject(outMessage);
	    inMessage = (Message) con.readObject();
	    con.close();

	    if (inMessage.getType() != Message.ACK) {
	        System.out.println("Invalid message type!");
	        System.exit(1);
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
