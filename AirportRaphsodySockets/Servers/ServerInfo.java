package Servers;

/**
 *
 * @author miguel
 */
public class ServerInfo {
	private int portNumber;
	private String hostName;

    /**
     *
     * @param portNumber
     * @param machineName
     */
    public ServerInfo(int portNumber, String machineName) {
		this.portNumber = portNumber;
		this.hostName = machineName;
	}

    /**
     *
     * @return
     */
    public int getPortNumber() {
		return portNumber;
	}

    /**
     *
     * @return
     */
    public String getHostName() {
		return hostName;
	}
}
