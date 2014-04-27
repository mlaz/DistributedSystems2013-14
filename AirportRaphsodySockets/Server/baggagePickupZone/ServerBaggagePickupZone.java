package Server.baggagePickupZone;

import Server.ClientMonitor;
import Server.ServerCom;
import Server.ServerInfo;

public class ServerBaggagePickupZone {
	private static int portNumber = 10003; //TODO change to right port
	private static String hostName;
	private static ServerInfo genRepInfo;
	
	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println("Usage: java ServerArrivalTerminal [thisMachineName] [genRepName] [genRepPort]");
			// System.exit(1);
			args = new String[3];
			args[0] = "localhost";
			args[1] = "localhost";
			args[2] = "10000";
		}
		
		/* obter parametros do problema */
		hostName = args[0];
		genRepInfo = new ServerInfo(Integer.parseInt(args[2]), args[1]);
		
		IBaggagePickupZoneGenRep genRep = new BaggagePickupZoneGenRepComm(genRepInfo, new ServerInfo(portNumber, hostName));
		
		/* establecer o serviço */
		genRep.setBaggagePickupZone();
		MBaggagePickupZone baggagePickup = new MBaggagePickupZone();
		ServerCom server = new ServerCom(portNumber);
        server.start();
        BaggagePickupZoneRequestsProcessor reqProcessor = new BaggagePickupZoneRequestsProcessor(baggagePickup);
        
        System.out.println("Baggage Pickup Zone service is listening on port " + portNumber + "...");
        
		/* iniciar processamento de serviçoes */
        ServerCom comm;
        ClientMonitor client;
		while (true) {
			comm = server.accept();
			client = new ClientMonitor(comm, reqProcessor);
			client.start();
		}
	}
}