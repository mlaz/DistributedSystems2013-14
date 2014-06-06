package Servers.genRep;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import Driver.EDriverStates;
import Passenger.EPassengerStates;
import Porter.EPorterStates;
import Utils.VectorClock;

/**
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 * Monitor do repositório geral de informação
 */
public class MGeneralRepository implements IGenRep {

	private int numPassengers;
	private int nBusSeats;
	private int busWaitTime;
	private int numFlights;
	private int maxBags;

	private FlightInfo plane;
	private PorterInfo porter;
	private DriverInfo driver;
	private PassengerInfo[] passengers;
	private int registeredPassengers;
	private boolean allPassReg;

	private Lock lock;
	private Condition porterDead;
	private Condition driverDead;
	private BufferedWriter bw;
	private List<String> logEvList;

    /**
     *
     * @param numPassengers
     * @param nBusSeats
     * @param busWaitTime
     * @param numFlights
     * @param maxBags
     * @param path
     */
    public MGeneralRepository(int numPassengers, int nBusSeats, int busWaitTime, int numFlights, int maxBags, String path) {
		passengers = null; // new PassengerInfo[numPassengers];
		registeredPassengers = 0;
		plane = null;
		
		this.numPassengers = numPassengers;
		this.nBusSeats = nBusSeats;
		this.busWaitTime = busWaitTime;
		this.numFlights = numFlights;
		this.maxBags = maxBags;
    	this.logEvList = new ArrayList<String>();

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
		
		printHeader();
	}
    
    public void planeFinished() {
    	for (String element : logEvList) {
    		try {
    			bw.write(element + "\n");
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}
    	logEvList = new ArrayList<String>();
    	
    }

    /**
     *
     */
    public void endSimulation() {
		try {
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Log written!");
	}

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

//	private void printLogEntry() {
//		String s = "";
//
//		s += (allPassReg) ? plane.toString() : "NLANDED ";
//		s += porter.toString();
//		s += driver.toString();
//		s += '\n';
//
//		if (allPassReg) // this avoids nullpointerexceptions during
//						// non-passenger threads state updates between flights
//			for (int i = 0; i < passengers.length; i++)
//				s += passengers[i].toString();
//
//		try {
//			bw.write(s + "\n");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

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

		logEvList.add(s);
	}

	/* porter */

    /**
     *
     */
    
	public void registerPorter() {
		lock.lock();
		try {
			porter = new PorterInfo();
		} finally {
			lock.unlock();
		}
	}

    /**
     *
     * @param newState
     */
    public void updatePorterState(EPorterStates newState, VectorClock clk) {
		lock.lock();
		try {
			porter.setStat(newState);
			addLogEntry(clk);
		} finally {
			lock.unlock();
		}
	}

    /**
     *
     */
    public void incLuggageAtCB(VectorClock clk) {
		lock.lock();
		try {
			porter.addconvBeltItem();
			addLogEntry(clk);
		} finally {
			lock.unlock();
		}
	}

    /**
     *
     */
    public void incLuggageAtSR(VectorClock clk) {
		lock.lock();
		try {
			porter.addStoredBaggage();
			addLogEntry(clk);
		} finally {
			lock.unlock();
		}
	}

    /**
     *
     */
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

    /**
     *
     */
    
	public void registerDriver() {
		lock.lock();
		try {
			driver = new DriverInfo(numPassengers, nBusSeats);
			// addLogEntry();
		} finally {
			lock.unlock();
		}
	}

    /**
     *
     * @param newState
     */
    public void updateDriverState(EDriverStates newState, VectorClock clk) {
		lock.lock();
		try {
			driver.setStat(newState);
			addLogEntry(clk);
		} finally {
			lock.unlock();
		}
	}

    /**
     *
     * @param queue
     */
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

    /**
     *
     * @param seats
     */
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

    /**
     *
     * @param pID
     * @param planeId
     * @param inTransit
     * @param startingLuggage
     */
    
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

    /**
     *
     * @param pID
     * @param newStat
     */
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

    /**
     *
     * @param pID
     */
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

    /**
     *
     * @return
     */
    public int getBusWaitTime() {
		return busWaitTime;
	}

    /**
     *
     * @return
     */
    public int getNumFlights() {
		return numFlights;
	}

    /**
     *
     * @return
     */
    public int getNumPassengers() {
		return numPassengers;
	}

    /**
     *
     * @return
     */
    public int getMaxBags() {
		return maxBags;
	}

    /**
     *
     * @return
     */
    public int getNumBusSeats() {
		return nBusSeats;
	}

    /**
     *
     */
    public void setPorterAsDead() {
		lock.lock();
		try {
			porter.setAsDead();
			porterDead.signal();
		} finally {
			lock.unlock();
		}
	}

    /**
     *
     */
    public void setDriverAsDead() {
		lock.lock();
		try {
			driver.setAsDead();
			driverDead.signal();
		} finally {
			lock.unlock();
		}
	}

    /**
     *
     */
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

    /**
     *
     */
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
}
