package Servers;

public class ServerInfo {
	private int portNumber;
	private String hostName;
	
	public ServerInfo(int portNumber, String machineName) {
		this.portNumber = portNumber;
		this.hostName = machineName;
	}

	public int getPortNumber() {
		return portNumber;
	}

	public String getHostName() {
		return hostName;
	}
}
