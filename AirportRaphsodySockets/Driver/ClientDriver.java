package Driver;

import Servers.ServerInfo;

/**
 * Classe ClientDriver: classe para lançar a thread TDriver
 * @author miguel
 */
public class ClientDriver {

    /**
     *
     * @param args genRepName genRepPort
     */
    public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Usage: java ClientDriver [genRepName] [genRepPort]");
			// System.exit(1);
			args = new String[2];
			args[0] = "localhost";
			args[1] = "22160";
		}
		
		ServerInfo genRepInfo = new ServerInfo( Integer.parseInt(args[1]), args[0] );
		IDriverGenRep genRep  = new CommDriverGenRep( genRepInfo );
		TDriver driver = new TDriver(genRep);
		driver.start();
		
		try {
			driver.join();
			genRep.setDriverAsDead();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
