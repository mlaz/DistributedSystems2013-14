package Utils;

public class HostPort {
	private int portNumber;
	private String hostName;
	
	public HostPort(String hostName, int portNum) {
		this.hostName   = hostName;
		this.portNumber = portNum;
	}

	public int getPortNumber() {
		return portNumber;
	}

	public String getHostName() {
		return hostName;
	}
}
