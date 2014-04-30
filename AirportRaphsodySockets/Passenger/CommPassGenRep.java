package Passenger;

import messages.Message;
import Client.ClientCom;
import Servers.ServerInfo;

public class CommPassGenRep implements IPassengerGenRep {

	private ServerInfo genRepInfo;
	
	private String myDebugName = "PASS_GENREP";
	
	public CommPassGenRep( ServerInfo genRepInfo ) {
		this.genRepInfo = genRepInfo;
	}
	
	@Override
	public IPassengerArrivalTerminal getArrivalTerminal() {
		ClientCom con = new ClientCom(genRepInfo.getHostName(), genRepInfo.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT, Message.GET_ARRIVAL_TERMINAL);
		
		printMessageSummary(outMessage, con, genRepInfo, true);
		
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		printMessageSummary(inMessage, con, genRepInfo, false);
		
		if (inMessage.getType() != Message.INT_STR) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
		ServerInfo arrTerm = new ServerInfo( inMessage.getInt1(), inMessage.getString() );
		return new CommPassArrivalTerminal( arrTerm );
	}

	@Override
	public IPassengerBaggageCollectionPoint getBaggagePickupZone() {
		ClientCom con = new ClientCom(genRepInfo.getHostName(), genRepInfo.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT, Message.GET_BAGGAGE_PICKUP_ZONE);
		
		printMessageSummary(outMessage, con, genRepInfo, true);
		
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		printMessageSummary(inMessage, con, genRepInfo, false);
		
		if (inMessage.getType() != Message.INT_STR) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
		ServerInfo collectionInfo = new ServerInfo( inMessage.getInt1(), inMessage.getString() );
		return new CommPassBaggageCollectionPoint( collectionInfo );
	}

	@Override
	public IPassengerBaggageReclaimGuichet getBaggageReclaimGuichet() {
		ClientCom con = new ClientCom(genRepInfo.getHostName(), genRepInfo.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT, Message.GET_BAGGAGE_RECLAIM_GUICHET);
		
		printMessageSummary(outMessage, con, genRepInfo, true);
		
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		printMessageSummary(inMessage, con, genRepInfo, false);
		
		if (inMessage.getType() != Message.INT_STR) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
		ServerInfo reclaimInfo = new ServerInfo( inMessage.getInt1(), inMessage.getString() );
		return new CommPassBaggageReclaimGuichet( reclaimInfo );
	}

	@Override
	public IPassengerArrivalExitTransferZone getArrivalTerminalExit() {
		ClientCom con = new ClientCom(genRepInfo.getHostName(), genRepInfo.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT, Message.GET_ARRIVAL_TERMINAL_EXIT);
		
		printMessageSummary(outMessage, con, genRepInfo, true);
		
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		printMessageSummary(inMessage, con, genRepInfo, false);
		
		if (inMessage.getType() != Message.INT_STR) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
		ServerInfo exitInfo = new ServerInfo( inMessage.getInt1(), inMessage.getString() );
		return new CommPassArrivalExitTransferZone( exitInfo );	}

	@Override
	public IPassengerDepartureTerminalEntrance getDepartureTerminalEntrace() {
		ClientCom con = new ClientCom(genRepInfo.getHostName(), genRepInfo.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT, Message.GET_DEPARTURE_TERMINAL_ENTRANCE);
		
		printMessageSummary(outMessage, con, genRepInfo, true);
		
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		printMessageSummary(inMessage, con, genRepInfo, false);
		
		if (inMessage.getType() != Message.INT_STR) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
		ServerInfo departureInfo = new ServerInfo( inMessage.getInt1(), inMessage.getString() );
		return new CommPassDepartureTerminalEntrance( departureInfo );
	}

	@Override
	public IPassengerBus getBus() {
		ClientCom con = new ClientCom(genRepInfo.getHostName(), genRepInfo.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT, Message.GET_BUS);
		
		printMessageSummary(outMessage, con, genRepInfo, true);
		
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		printMessageSummary(inMessage, con, genRepInfo, false);
		
		if (inMessage.getType() != Message.INT_STR) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
		ServerInfo busInfo = new ServerInfo( inMessage.getInt1(), inMessage.getString() );
		return new CommPassBus( busInfo );
	}

	@Override
	public void registerPassenger(int passengerNumber, int flightNumber, boolean inTransit, int remainingBags) {
		ClientCom con = new ClientCom(genRepInfo.getHostName(), genRepInfo.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT_INT_INT_INT_BOOL, Message.REGISTER_PASSENGER, passengerNumber, flightNumber, remainingBags, inTransit);
		
		printMessageSummary(outMessage, con, genRepInfo, true);
		
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		printMessageSummary(inMessage, con, genRepInfo, false);
		
		if (inMessage.getType() != Message.ACK) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
	}

	@Override
	public void gotLuggage(int passengerNumber) {
		ClientCom con = new ClientCom(genRepInfo.getHostName(), genRepInfo.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT_INT, Message.GOT_LUGGAGE, passengerNumber);
		
		printMessageSummary(outMessage, con, genRepInfo, true);
		
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		printMessageSummary(inMessage, con, genRepInfo, false);
		
		if (inMessage.getType() != Message.ACK) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}		
	}

	@Override
	public void setPassengerStat(int passengerNumber, EPassengerStates state) {
		ClientCom con = new ClientCom(genRepInfo.getHostName(), genRepInfo.getPortNumber());
		Message inMessage, outMessage;

		while (!con.open()) {
			try {
				Thread.sleep((long) (10));
			} catch (InterruptedException e) {
			}
		}

		outMessage = new Message(Message.INT_INT_STR, Message.SET_PASSENGER_STAT, passengerNumber, state.name());
		
		printMessageSummary(outMessage, con, genRepInfo, true);
		
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		printMessageSummary(inMessage, con, genRepInfo, false);
		
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
