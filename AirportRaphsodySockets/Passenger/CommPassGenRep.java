package Passenger;

import messages.Message;
import Client.ClientCom;
import Server.ServerInfo;

public class CommPassGenRep implements IPassengerGenRep {

	private ServerInfo genRepInfo;
	
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
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

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
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

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
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

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
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

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
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

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
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

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
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

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
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

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

		outMessage = new Message(Message.INT_INT_STR, Message.SET_PASSENGER_STAT, state.name());
		con.writeObject(outMessage);
		inMessage = (Message) con.readObject();
		con.close();

		if (inMessage.getType() != Message.ACK) {
			System.out.println("Invalid message type!");
			System.exit(1);
		}
	}

}
