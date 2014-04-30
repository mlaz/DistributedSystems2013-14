package Servers.clientsInterfaces;

import Servers.EDriverStates;
import Servers.ServerInfo;

public interface IDriverGenRep {

	void updateDriverState(EDriverStates state);

	ServerInfo getArrivalTerminalExit();

	ServerInfo getBus();

	void registerDriver();

}
