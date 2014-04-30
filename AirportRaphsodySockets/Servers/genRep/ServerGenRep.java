package Servers.genRep;

import Servers.ClientMonitor;
import Servers.ServerCom;

/**
 *
 * @author miguel
 */
public class ServerGenRep {

	private static int portNumber = 22160;
	private static String logFile;

    /**
     *
     * @param args
     */
    public static void main(String[] args) {

		for( String arg : args ) {
			System.out.println(arg);
		}
		
		if (args.length != 6) {
			System.out.println("Usage: java ServerGenRep [logFile] [numFlights] [numPassengers] [numBusSeats] [maxBags] [busTimer_in_ms]");
			// System.exit(1);
			args = new String[6];
			args[0] = "log.log";
			args[1] = "5";
			args[2] = "1";
			args[3] = "3";
			args[4] = "2";
			args[5] = "2000";
		}
		
		/* obter parametros do problema */
		logFile	 			= args[0];
		int numFlights 		= Integer.parseInt(args[1]);
		int numPassengers 	= Integer.parseInt(args[2]);
		int numSeats 		= Integer.parseInt(args[3]);
		int maxBags 		= Integer.parseInt(args[4]);
		int busTimer 		= Integer.parseInt(args[5]);		
		
		/* establecer o serviço */
		MGeneralRepository genRep = new MGeneralRepository(numPassengers, numSeats, busTimer, numFlights, maxBags, logFile);
		
		ServerCom server = new ServerCom(portNumber);
		server.start();
		GenRepRequestsProcessor reqProcessor = new GenRepRequestsProcessor(genRep);
		
		System.out.println("General Repository service is listening on port " + portNumber + "...");

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