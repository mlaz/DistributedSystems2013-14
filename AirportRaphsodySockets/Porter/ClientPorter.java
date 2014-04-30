package Porter;

import Servers.ServerInfo;

/**
 *
 * @author miguel
 */
public class ClientPorter {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Usage: java ClientPorter [genRepName] [genRepPort]");
			// System.exit(1);
			args = new String[2];
			args[0] = "localhost";
			args[1] = "22160";
		}
		
		ServerInfo genRepInfo = new ServerInfo( Integer.parseInt(args[1]), args[0] );
		IPorterGenRep genRep  = new CommPorterGenRep( genRepInfo );
		TPorter porter = new TPorter(genRep);
		porter.start();
		
		try {
			porter.join();
			genRep.setPorterAsDead();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
