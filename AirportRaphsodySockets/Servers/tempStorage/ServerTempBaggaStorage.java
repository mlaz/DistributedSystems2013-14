package Servers.tempStorage;

import Servers.ClientMonitor;
import Servers.ServerCom;
import Servers.ServerInfo;

/**
 * Classe de servidor com replicação para receção de pedidos ao monior por parte das threads(clientes)
 * @author miguel
 */
public class ServerTempBaggaStorage {
	private static int portNumber = 22167;
	private static String hostName;
	private static ServerInfo genRepInfo;

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
		
		if (args.length != 3) {
			System.out.println("Usage: java ServerTempBaggageStorage [thisMachineName] [genRepName] [genRepPort]");
			// System.exit(1);
			args = new String[3];
			args[0] = "localhost";
			args[1] = "localhost";
			args[2] = "22160";
		}
		/* obter parametros do problema */
		hostName = args[0];
		genRepInfo = new ServerInfo(Integer.parseInt(args[2]), args[1]);
		
		ITempBaggageStorageGenRep genRep = new TempBaggageStorageGenRepComm(genRepInfo, new ServerInfo(portNumber, hostName));
		
		/* establecer o serviço */
		genRep.setTempStorage();
		MTempBaggageStorage tempStorage = new MTempBaggageStorage();
		ServerCom server = new ServerCom(portNumber);
        server.start();
        TempBaggageStorageRequestsProcessor reqProcessor = new TempBaggageStorageRequestsProcessor(tempStorage);
        
        System.out.println("Temporary Baggage Storage service is listening on port " + portNumber + "...");
        
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