package Passenger;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import Servers.genRep.IGenRep;
import Utils.RmiUtils;


/**
 * Classe ClientDriver: classe para lançar todas as threads TPassenger
 * @author miguel
 */
public class ClientPassengers {
	
	final static String usage = "Usage: java ClientPassengers [RMIRegName] [RMIRegPort]";
    /**
     *
     * @param args  [genRepName] [genRepPort]
     */
    public static void main(String[] args) {
    	if (args.length != 1) {
			System.out.println(usage);
			// System.exit(1);
			args = new String[1];
			args[0] = "localhost";
		}
		/* obter parametros do problema */		
		IGenRep genRep = null;
		IPassengerArrivalTerminal arrivalTerminal = null;
		IPassengerBaggageCollectionPoint luggageCollectionPoint = null;
		IPassengerBaggageReclaimGuichet baggageReclaimOffice = null;
		IPassengerArrivalExitTransferZone arrivalTerminalExit = null;
		IPassengerDepartureTerminalEntrance departureTerminalEntrace = null;
		IPassengerBus bus = null;
		
		try {
			Registry genRepRegistry = LocateRegistry.getRegistry(args[0], RmiUtils.rmiPort);
			genRep = (IGenRep) genRepRegistry.lookup(RmiUtils.genRepId);
			System.out.println( "GenRep RMI registry accessed" );

			String arrivalTerminalLocation = genRep.getServiceLocation(RmiUtils.arrivalTerminalId);
			Registry arrivalTerminalRegistry = LocateRegistry.getRegistry(arrivalTerminalLocation, RmiUtils.rmiPort);
			arrivalTerminal = (IPassengerArrivalTerminal) arrivalTerminalRegistry.lookup(RmiUtils.arrivalTerminalId);
			
			String luggageCollectionPointLocation = genRep.getServiceLocation(RmiUtils.baggagePickupZoneId);
			Registry luggageCollectionPointRegistry = LocateRegistry.getRegistry(luggageCollectionPointLocation, RmiUtils.rmiPort);
			luggageCollectionPoint = (IPassengerBaggageCollectionPoint) luggageCollectionPointRegistry.lookup(RmiUtils.baggagePickupZoneId);
			
			String baggageReclaimOfficeLocation = genRep.getServiceLocation(RmiUtils.baggageReclaimGuichetId);
			Registry baggageReclaimOfficeRegistry = LocateRegistry.getRegistry(baggageReclaimOfficeLocation, RmiUtils.rmiPort);
			baggageReclaimOffice = (IPassengerBaggageReclaimGuichet) baggageReclaimOfficeRegistry.lookup(RmiUtils.baggageReclaimGuichetId);
			
			String arrivalTerminalExitLocation = genRep.getServiceLocation(RmiUtils.arrivalTerminalTransferZoneId);
			Registry arrivalTerminalExitRegistry = LocateRegistry.getRegistry(arrivalTerminalExitLocation, RmiUtils.rmiPort);
			arrivalTerminalExit = (IPassengerArrivalExitTransferZone) arrivalTerminalExitRegistry.lookup(RmiUtils.arrivalTerminalTransferZoneId);
			
			String departureTerminalEntraceLocation = genRep.getServiceLocation(RmiUtils.departureTerminalEntraceZoneId);
			Registry departureTerminalEntraceRegistry = LocateRegistry.getRegistry(departureTerminalEntraceLocation, RmiUtils.rmiPort);
			departureTerminalEntrace = (IPassengerDepartureTerminalEntrance) departureTerminalEntraceRegistry.lookup(RmiUtils.departureTerminalEntraceZoneId);
			
			String busLocation = genRep.getServiceLocation(RmiUtils.busId);
			Registry busRegistry = LocateRegistry.getRegistry(busLocation, RmiUtils.rmiPort);
			bus = (IPassengerBus) busRegistry.lookup(RmiUtils.busId);

		} catch (AccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NotBoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		int N = 0;
        int M = 0;
        int K = 0;
		try {
			K = genRep.getNumFlights();
			N = genRep.getNumPassengers();
			M = genRep.getMaxBags();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		TPassenger[][] passengerList =  new TPassenger[K][N];        
		int flightNumber;
		int passNumber;
		int nbags = M;
		
		Boolean transit = true;
		for(flightNumber = 0; flightNumber < K; flightNumber++){
		//generating passengers         
		        for (passNumber = 0; passNumber < N; passNumber++) {
		                passengerList[flightNumber][passNumber] = 
		                		new TPassenger(passNumber,
		                				nbags, 
		                				transit, 
		                				flightNumber,
		                				N + 2,
		                				passNumber + 2,
		                				(IPassengerGenRep) genRep,
		                				arrivalTerminal,
		                	    		luggageCollectionPoint,
		                	    		baggageReclaimOffice,
		                	    		arrivalTerminalExit,
		                	    		departureTerminalEntrace,
		                	    		bus);
		        
		                transit = !transit;
		                nbags = (nbags == M) ? 0 : nbags + 1;
		        }
		        
		        System.out.println("flight# " + flightNumber + " NPASSENGERS:" + N);
		        //starting threads
		        System.out.println("NEW airplane-----------------------------------------\n");
		        
		        for (passNumber = 0; passNumber < N; passNumber++) 
		                passengerList[flightNumber][passNumber].start();
		                
		                
		        for (passNumber = 0; passNumber < N; passNumber++)
		                try {
		                        passengerList[flightNumber][passNumber].join();
		                } catch (InterruptedException e) {
		                        // TODO Auto-generated catch block
		                        e.printStackTrace();
		                }
		        try {
					genRep.planeFinished();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        System.out.println("ALL Passengers Done-----------------------------------------\n");
		}
		
		// WAIT FOR PORTER AND DRIVER TO DIE
		System.out.print("Waiting for porter to die... ");
		try {
			System.out.print("Waiting for porter to die... ");
			genRep.waitForPorterToDie();
			System.out.println(" Dead!");
			System.out.print("Waiting for driver to die... ");
			genRep.waitForDriverToDie();
			System.out.println(" Dead!");
	        System.out.println("ALL Threads Done-----------------------------------------\n");
	        genRep.endSimulation();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.exit(0);
	}
	

}
