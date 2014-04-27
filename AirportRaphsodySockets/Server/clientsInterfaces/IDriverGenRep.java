package Server.clientsInterfaces;

import Server.EDriverStates;
import Server.ServerInfo;

public interface IDriverGenRep {

	void updateDriverState(EDriverStates state);

	ServerInfo getArrivalTerminalExit();

	ServerInfo getBus();

	void registerDriver();

}
