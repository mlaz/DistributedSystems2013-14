package Servers.genRep;

import messages.Message;
import Servers.EDriverStates;
import Servers.EPassengerStates;
import Servers.EPorterStates;
import Servers.ServerInfo;
import Servers.clientsInterfaces.IRequestProcessor;

/**
 * Classe Para processameto de mensagens para o monitor MGenRep
 * @author miguel
 */
public class GenRepRequestsProcessor implements IRequestProcessor {
	private final int[] validTypes = {Message.INT, Message.INT_INT, Message.INT_INT_STR, Message.INT_STR, Message.INT_INT_INT_INT_BOOL, Message.INT_INTARR};
	private MGeneralRepository genRep;

    /**
     *
     * @param genRep
     */
    public GenRepRequestsProcessor( MGeneralRepository genRep) {
		this.genRep = genRep; 
	}

    /**
     *
     * @param fromClient
     * @return
     * @throws InterruptedException
     */
    public Message processAndReply(Message fromClient) throws InterruptedException {
		System.out.println("GEN REP RECIEVED:");
		fromClient.print();
		
		/* validate message */
		if( !isValidType(fromClient.getType()) ) {
			System.err.println("This message shouldn't be here");
		}
		
		Message fromServer = null;
		
		/* process and reply */
		ServerInfo server;
		int port;
		String host;
		
		switch( fromClient.getInt1() ) {
			case Message.END_SIMULATION:
			    genRep.endSimulation();
			    fromServer = new Message(Message.ACK);
			    break;
			case Message.GET_NUM_FLIGHTS:
				fromServer = new Message(Message.INT, genRep.getNumFlights());
				break;
			case Message.GET_NUM_PASSENGERS:
				fromServer = new Message(Message.INT, genRep.getNumPassengers());
				break;
			case Message.GET_MAX_BAGS:
				fromServer = new Message(Message.INT, genRep.getMaxBags());
				break;
			case Message.GET_NUM_SEATS:
				fromServer = new Message(Message.INT, genRep.getNumBusSeats());
				break;
			case Message.GET_ARRIVAL_TERMINAL:
			    server = genRep.getArrivalTerminal();
			    fromServer = new Message(Message.INT_STR, server.getPortNumber(), server.getHostName());
			    break;
			case Message.GET_ARRIVAL_TERMINAL_EXIT:
				server = genRep.getArrivalTerminalExit();
			    fromServer = new Message(Message.INT_STR, server.getPortNumber(), server.getHostName());
			    break;
			case Message.GET_BAGGAGE_PICKUP_ZONE:
				server = genRep.getBaggagePickupZone();
			    fromServer = new Message(Message.INT_STR, server.getPortNumber(), server.getHostName());
			    break;
			case Message.GET_BAGGAGE_RECLAIM_GUICHET:
				server = genRep.getBaggageReclaimGuichet();
			    fromServer = new Message(Message.INT_STR, server.getPortNumber(), server.getHostName());
			    break;
			case Message.GET_BUS:
				server = genRep.getBus();
			    fromServer = new Message(Message.INT_STR, server.getPortNumber(), server.getHostName());
			    break;
			case Message.GET_DEPARTURE_TERMINAL_ENTRANCE:
				server = genRep.getDepartureTerminalEntrace();
			    fromServer = new Message(Message.INT_STR, server.getPortNumber(), server.getHostName());
			    break;
			case Message.GET_TEMP_BAGGAGE_STORAGE:
				server = genRep.getTempBaggageStorage();
			    fromServer = new Message(Message.INT_STR, server.getPortNumber(), server.getHostName());
			    break;
			case Message.GET_BUS_DEPARTURE_INTERVAL:
				fromServer = new Message(Message.INT, genRep.getBusTimer());
				break;
			case Message.GOT_LUGGAGE:
			    genRep.gotLuggage(fromClient.getInt2());
			    fromServer = new Message(Message.ACK);
			    break;
			case Message.INC_LUGGAGE_AT_CB:
			    genRep.incLuggageAtCB();
			    fromServer = new Message(Message.ACK);
			    break;
			case Message.INC_LUGGAGE_AT_SR:
			    genRep.incLuggageAtSR();
			    fromServer = new Message(Message.ACK);
			    break;
			case Message.REGISTER_DRIVER:
			    genRep.registerDriver();
			    fromServer = new Message(Message.ACK);
			    break;
			case Message.REGISTER_PASSENGER:
			    genRep.registerPassenger(fromClient.getInt2(), fromClient.getInt3(), fromClient.getBool(), fromClient.getInt4());
			    fromServer = new Message(Message.ACK);
			    break;
			case Message.REGISTER_PORTER:
			    genRep.registerPorter();
			    fromServer = new Message(Message.ACK);
			    break;
			case Message.REMOVE_LUGGAGE_AT_PLANE:
			    genRep.removeLuggageAtPlane();
			    fromServer = new Message(Message.ACK);
			    break;
			case Message.SET_ARRIVAL_TERMINAL:
				port = fromClient.getInt2();
				host = fromClient.getString();
			    genRep.setArrivalTerminal(new ServerInfo(port, host));
			    fromServer = new Message(Message.ACK);
			    break;
			case Message.SET_ARRIVAL_TERMINAL_EXIT:
				port = fromClient.getInt2();
				host = fromClient.getString();
			    genRep.setArrivalTerminalExit(new ServerInfo(port, host));
			    fromServer = new Message(Message.ACK);
			    break;
			case Message.SET_BAGGAGE_PICKUP_ZONE:
				port = fromClient.getInt2();
				host = fromClient.getString();
			    genRep.setBaggagePickupZone(new ServerInfo(port, host));
			    fromServer = new Message(Message.ACK);
			    break;
			case Message.SET_BAGGAGE_RECLAIM_GUICHET:
				port = fromClient.getInt2();
				host = fromClient.getString();
			    genRep.setBaggageReclaimGuichet(new ServerInfo(port, host));
			    fromServer = new Message(Message.ACK);
			    break;
			case Message.SET_BUS:
				port = fromClient.getInt2();
				host = fromClient.getString();
			    genRep.setBus(new ServerInfo(port, host));
			    fromServer = new Message(Message.ACK);
			    break;
			case Message.SET_DEPARTURE_TERMINAL_ENTRANCE:
				port = fromClient.getInt2();
				host = fromClient.getString();
			    genRep.setDepartureTerminalEntrace(new ServerInfo(port, host));
			    fromServer = new Message(Message.ACK);
			    break;
			case Message.SET_TEMP_BAGGAGE_STORAGE:
				port = fromClient.getInt2();
				host = fromClient.getString();
			    genRep.setTempBaggageStorage(new ServerInfo(port, host));
			    fromServer = new Message(Message.ACK);
			    break;
			case Message.SET_PASSENGER_STAT:
			    genRep.setPassengerStat(fromClient.getInt2(), EPassengerStates.valueOf(fromClient.getString()));
			    fromServer = new Message(Message.ACK);
			    break;
			case Message.UPDATE_DRIVER_QUEUE:
			    genRep.updateDriverQueue(fromClient.getIntArr());
			    fromServer = new Message(Message.ACK);
			    break;
			case Message.UPDATE_DRIVER_SEATS:
			    genRep.updateDriverSeats(fromClient.getIntArr());
			    fromServer = new Message(Message.ACK);
			    break;
			case Message.UPDATE_DRIVER_STATE:
			    genRep.updateDriverState(EDriverStates.valueOf(fromClient.getString()));
			    fromServer = new Message(Message.ACK);
			    break;
			case Message.UPDATE_PORTER_STATE:
				genRep.updatePorterState(EPorterStates.valueOf(fromClient.getString()));
			    fromServer = new Message(Message.ACK);
			    break;
			case Message.SET_PORTER_AS_DEAD:
				genRep.setPorterAsDead();
				fromServer = new Message(Message.ACK);
				break;
			case Message.SET_DRIVER_AS_DEAD:
				genRep.setDriverAsDead();
				fromServer = new Message(Message.ACK);
				break;
			case Message.WAIT_FOR_DRIVER_TO_DIE:
				genRep.waitForDriverToDie();
				fromServer = new Message(Message.ACK);
				break;
			case Message.WAIT_FOR_PORTER_TO_DIE:
				genRep.waitForPorterToDie();
				fromServer = new Message(Message.ACK);
				break;
			default:
				System.out.println("Unknown action! Exiting.");
				System.exit(1);
		}
		System.out.println("GEN REN IS RESPONDING WITH:");
		fromServer.print();
		return fromServer;
	}
	
	private boolean isValidType(int type) {
		return contains( validTypes, type );
	}
	
	private boolean contains(int[] list, int value) {
		for(int v:list) {
			if( value == v ) {
				return true;
			}
		}
		
		return false;
	}
}
