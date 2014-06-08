package Servers.genRep;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import Driver.EDriverStates;
import Passenger.EPassengerStates;
import Porter.EPorterStates;
import Utils.ClockTuple;
import Utils.VectorClock;

/**
 * Class that implements the General Repository server services.
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 */
/**
 * @author fteixeira
 *
 */
public class MGeneralRepository implements IGenRep {

	/**
	 * The number of passengers in each flight
	 */
	private int numPassengers;
	/**
	 * The number of seats in the bus
	 */
	private int nBusSeats;
	/**
	 * The time, in mili-seconds, that the bus waits for new passengers
	 */
	private int busWaitTime;
	/**
	 * The total number of flights
	 */
	private int numFlights;
	/**
	 * The maximum number of bags that each passenger may have
	 */
	private int maxBags;
	/**
	 * The current logging information of the plane
	 */
	private FlightInfo plane;
	/**
	 * The current logging information of the porter
	 */
	private PorterInfo porter;
	/**
	 * The current logging information of the driver
	 */
	private DriverInfo driver;
	/**
	 * The current logging information of the passengers
	 */
	private PassengerInfo[] passengers;
	/**
	 * The number of passengers registered
	 */
	private int registeredPassengers;
	/**
	 * True if all passengers have already registered
	 */
	private boolean allPassReg;
	/**
	 * The lock
	 */
	private Lock lock;
	/**
	 * Waiting condition for the porter to be dead
	 */
	private Condition porterDead;
	/**
	 * Waiting condition for the driver to be dead
	 */
	private Condition driverDead;
	/**
	 * Waiting condition for the driver and porter to finish the current plane
	 */
	private Condition ready4NxtPlane;
	/**
	 * True if the porter is ready for a new plane
	 */
	private boolean porterReady;
	/**
	 * True if the driver is ready for a new plane
	 */
	private boolean driverReady;	
	/**
	 * File writer
	 */
	private BufferedWriter bw;
	/**
	 * Stores all the events of the current plane to then sort using the vectorial clocks
	 */
	private List<ClockTuple<String>> logEvList;
	/**
	 * Stores the registered services location
	 */
	private Map<String, String> services;

    /**
     * Instanciates a MGeneralRepository object.
     * @param numPassengers The number of passengers in each flight
     * @param nBusSeats The number of seats on the bus
     * @param busWaitTime The time, in mili-seconds, that the bus waits for new passengers
     * @param numFlights The number of flights in the simulation
     * @param maxBags The maximum number of bags that each passenger may have
     * @param path The path to the log file
     */
    public MGeneralRepository(int numPassengers, int nBusSeats, int busWaitTime, int numFlights, int maxBags, String path ) {
		passengers = null; // new PassengerInfo[numPassengers];
		registeredPassengers = 0;
		plane = null;
		
		this.numPassengers = numPassengers;
		this.nBusSeats = nBusSeats;
		this.busWaitTime = busWaitTime;
		this.numFlights = numFlights;
		this.maxBags = maxBags;
    	this.logEvList = new ArrayList<>();
    	this.porterReady = false;
    	this.driverReady = false;
    	this.services = new HashMap<>();
    	
		allPassReg = false;
		//
		File file = new File(path);

		if (!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		try {
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			bw = new BufferedWriter(fw);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lock = new ReentrantLock();
		porterDead = lock.newCondition();
		driverDead = lock.newCondition();
		ready4NxtPlane = lock.newCondition();
		
		printHeader();
	}
    
	public void planeFinished() {

		lock.lock();
		try {
			while (!(driverReady & porterReady)) {
				ready4NxtPlane.await();
			}
			
			Collections.sort(logEvList);

			for (ClockTuple<String> element : logEvList) {
				try {
					bw.write(element.getData() + "\n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			logEvList = new ArrayList<>();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

    public void endSimulation() {
		try {
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Log written!");
	}

	/**
	 * Prints the header of the log file 
	 */
	private void printHeader() {
		try {

			bw.write("             AIRPORT RHAPSODY - Description of the internal state of the problem\n");
			bw.write("\n");
			bw.write("PLANE    PORTER                  DRIVER\n");
			bw.write("FN BN  Stat CB SR   Stat  Q1 Q2 Q3 Q4 Q5 Q6  S1 S2 S3\n");
			bw.write("                                                         PASSENGERS\n");
			bw.write("St1 Si1 NR1 NA1 St2 Si2 NR2 NA2 St3 Si3 NR3 NA3 St4 Si4 NR4 NA4 St5 Si5 NR5 NA5 St6 Si6 NR6 NA6\n");

		} catch (IOException e) {
			System.out.println("ERROR: Couldnt print header to logfile");
			e.printStackTrace();
		}
	}

	/**
	 * Adds a new event to logEvList.
	 * @param clk The clock associated with the event
	 */
	private void addLogEntry(VectorClock clk) {
		String s = "";

		s += (allPassReg) ? plane.toString() : "NLANDED ";
		s += porter.toString();
		s += driver.toString();
		s += '\n';

		if (allPassReg) // this avoids nullpointerexceptions during
						// non-passenger threads state updates between flights
			for (int i = 0; i < passengers.length; i++)
				s += passengers[i].toString();

		logEvList.add( new ClockTuple<String>(s, clk));
	}

	public void registerPorter() {
		lock.lock();
		try {
			porter = new PorterInfo();
		} finally {
			lock.unlock();
		}
	}

    public void updatePorterState(EPorterStates newState, VectorClock clk) {
		lock.lock();
		try {
			porter.setStat(newState);
			addLogEntry(clk);
		} finally {
			lock.unlock();
		}
	}

    public void incLuggageAtCB(VectorClock clk) {
		lock.lock();
		try {
			porter.addconvBeltItem();
			addLogEntry(clk);
		} finally {
			lock.unlock();
		}
	}

    public void incLuggageAtSR(VectorClock clk) {
		lock.lock();
		try {
			porter.addStoredBaggage();
			addLogEntry(clk);
		} finally {
			lock.unlock();
		}
	}

    public void removeLuggageAtPlane(VectorClock clk) {
		lock.lock();
		try {
			plane.removeABag();
			addLogEntry(clk);
		} finally {
			lock.unlock();
		}
	}

	/* driver */

	public void registerDriver() {
		lock.lock();
		try {
			driver = new DriverInfo(numPassengers, nBusSeats);
			// addLogEntry();
		} finally {
			lock.unlock();
		}
	}

    public void updateDriverState(EDriverStates newState, VectorClock clk) {
		lock.lock();
		try {
			driver.setStat(newState);
			addLogEntry(clk);
		} finally {
			lock.unlock();
		}
	}

    public void updateDriverQueue(int[] queue, VectorClock clk) {
		lock.lock();
		try {
			int[] iqueue = new int[numPassengers];
			int i = 0;
			while (i < queue.length) {
				iqueue[i] = queue[i];
				i++;
			}
			while (i < numPassengers) {
				iqueue[i] = -1;
				i++;
			}

			driver.setQueueIDs(iqueue);
			addLogEntry(clk);
		} finally {
			lock.unlock();
		}
	}

    public void updateDriverSeats(int[] seats, VectorClock clk) {
		lock.lock();
		try {
			driver.setSeatsIDs(seats);
			addLogEntry(clk);
		} finally {
			lock.unlock();
		}
	}

	/* passengers */

	public void registerPassenger(int pID, int planeId, boolean inTransit,
			int startingLuggage) {

		lock.lock();
		try {
			if (plane != null) {
				if (plane.getFlightID() != planeId) {
					allPassReg = false;
					plane = new FlightInfo(planeId);
					passengers = new PassengerInfo[numPassengers];
					registeredPassengers = 0;
				}
			} else {
				plane = new FlightInfo(planeId);
				passengers = new PassengerInfo[numPassengers];
			}

			passengers[registeredPassengers] = new PassengerInfo(pID,
					inTransit, startingLuggage);
			registeredPassengers++;
			plane.addBaggage(startingLuggage);
			allPassReg = (registeredPassengers == numPassengers);
		} finally {
			lock.unlock();
		}
	}

    public void setPassengerStat(int pID, EPassengerStates newStat, VectorClock clk) {
		lock.lock();
		try {
			PassengerInfo p = findPassenger(pID);
			p.setStat(newStat);
			addLogEntry(clk);
		} finally {
			lock.unlock();
		}
	}

    public void gotLuggage(int pID, VectorClock clk) {
		lock.lock();
		try {
			PassengerInfo p = findPassenger(pID);
			p.gotLuggage();
			addLogEntry(clk);
		} finally {
			lock.unlock();
		}
    }

	private PassengerInfo findPassenger(int pID) {
		for (PassengerInfo p : passengers) {
			if (p.getId() == pID)
				return p;
		}

		return null;
	}

    public int getBusWaitTime() {
		return busWaitTime;
	}

    public int getNumFlights() {
		return numFlights;
	}

    public int getNumPassengers() {
		return numPassengers;
	}

    public int getMaxBags() {
		return maxBags;
	}

    public int getNumBusSeats() {
		return nBusSeats;
	}

    public void setPorterAsDead() {
		lock.lock();
		try {
			porter.setAsDead();
			porterDead.signal();
		} finally {
			lock.unlock();
		}
	}

    public void setDriverAsDead() {
		lock.lock();
		try {
			driver.setAsDead();
			driverDead.signal();
		} finally {
			lock.unlock();
		}
	}

    public void waitForDriverToDie() {
		lock.lock();
		try {
			while(!driver.isDead()) {
				driverDead.await();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

    public void waitForPorterToDie() {
		lock.lock();
		try {
			while(!porter.isDead()) {
				porterDead.await();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	@Override
	public void driverWaiting4Plane() throws RemoteException {
		lock.lock();
		try {
			driverReady = true;
			ready4NxtPlane.signal();
		} finally {
			lock.unlock();
		}
	}

	@Override
	public void porterWaiting4Plane() throws RemoteException {
		lock.lock();
		try {
			porterReady = true;
			ready4NxtPlane.signal();
		} finally {
			lock.unlock();
		}		
	}

	@Override
	public void registerService(String serviceName, String hostName) throws RemoteException {
		services.put(serviceName, hostName);
		System.out.println(serviceName + " registered at " + hostName);
	}
	
	public String getServiceLocation(String serviceName) throws RemoteException {
		return services.get(serviceName);
	}
}
