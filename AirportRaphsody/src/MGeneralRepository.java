import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 *
 */
public class MGeneralRepository {
	
	private MArrivalTerminal arrivalTerminal;
	private MArrivalTerminalExit arrivalTerminalExit;
	private MBaggagePickupZone baggagePickupZone;
	private MBaggageReclaimGuichet baggageReclaimGuichet;
	private MBus bus;
	private MTempBaggageStorage tempBaggageStorage;
	
        private LinkedList<String> log;
        
        private FlightInfo plane;
        private PorterInfo porter;
        private DriverInfo driver;
        private PassengerInfo[] passengers;
        private int registeredPassengers;
        
	public MGeneralRepository(int numPassengers) {
                passengers = new PassengerInfo[numPassengers];
                registeredPassengers = 0;
                log = new LinkedList<>();
	}
        
        public void saveToFile(String path) {
            try {
                File file = new File(path);

                if (!file.exists()) {
                    file.createNewFile();
                }

                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);

                bw.write("             AIRPORT RHAPSODY - Description of the internal state of the problem\n");
                bw.write("\n");
                bw.write("PLANE    PORTER                  DRIVER\n");
                bw.write("FN BN  Stat CB SR   Stat  Q1 Q2 Q3 Q4 Q5 Q6  S1 S2 S3\n");
                bw.write("                                                         PASSENGERS\n");
                bw.write("St1 Si1 NR1 NA1 St2 Si2 NR2 NA2 St3 Si3 NR3 NA3 St4 Si4 NR4 NA4 St5 Si5 NR5 NA5 St6 Si6 NR6 NA6\n");
                
                
                for(int i=0 ; i<log.size() ; i++ ) {
                    bw.write(log.poll()+"\n");
                }
                bw.close();

                System.out.println("Log written!");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        private void addLogEntry() {
            String s = "";
            
            s += plane.toString();
            s += porter.toString();
            s += driver.toString();
            s += '\n';
            
            for(int i=0 ; i<passengers.length ; i++) {
                s += passengers[i].toString();
            }
            
            log.add(s);
        }
        
        /* plane  */
        public synchronized void registerNewFlight(int flightID, int numLuggage) {
            plane = new FlightInfo(flightID, numLuggage);
            registeredPassengers = 0;
            porter = new PorterInfo("----",0,0);
            driver = new DriverInfo("----",0,0);
            
            for(int i=0 ; i<passengers.length ; i++) {
                passengers[i] = new PassengerInfo(9, "---", "---", 0);
            }
            addLogEntry();
        }
        
        /* porter */
        public synchronized void registerPorter() {
            porter = new PorterInfo("WPTL",0,0);
            addLogEntry();
        }
        
        public synchronized void setPorterState(String newState) {
            porter.setStat(newState);
            addLogEntry();
        }
        
        public synchronized void setLuggageAtCB(int cb) {
            porter.setCb(cb);
            addLogEntry();
        }
        
        public synchronized void setLuggageAtSR(int sr) {
            porter.setSr(sr);
            addLogEntry();
        }
        
        /* driver */
        public synchronized void registerDriver(int queueSize, int numSeats) {
            driver = new DriverInfo("PAAT", queueSize, numSeats);
            addLogEntry();
        }
        
        public synchronized void setDriverState(String newState) {
            driver.setStat(newState);
            addLogEntry();
        }
        
        public synchronized void setDriverQueue(int[] queue) {
            driver.setQueueIDs(queue);
            addLogEntry();
        }
        
        public synchronized void setDriverSeats(int[] seats) {
            driver.setSeatsIDs(seats);
            addLogEntry();
        }
        
        /* passengers */
        public synchronized void registerPassenger(int pID, String stat, String situation, int startingLuggage) {
            passengers[registeredPassengers] = new PassengerInfo(pID, stat, situation, startingLuggage);
            registeredPassengers++;
            addLogEntry();
        }
        
        public synchronized void setPassengerStat(int pID, String newStat) {
            PassengerInfo p = findPassenger(pID);
            p.setStat(newStat);
            addLogEntry();
        }
        
        public synchronized void setPassengerCurrentLuggage(int pID, int currentLuggage) {
            PassengerInfo p = findPassenger(pID);
            p.setCurrentLuggage(currentLuggage);
            addLogEntry();
        }
        
        private PassengerInfo findPassenger(int pID) {
            for(PassengerInfo p:passengers) {
                if( p.getId() == pID )
                    return p;
            }
            
            return null;
        }
        
        
        
	/**
	 * @return the arrivalTerminalExit
	 */
	public MArrivalTerminalExit getArrivalTerminalExit() {
		return arrivalTerminalExit;
	}
	
	/**
	 * @param arrivalTerminalExit the arrivalTerminalExit to set
	 */
	public void setArrivalTerminalExit(MArrivalTerminalExit arrivalTerminalExit) {
		this.arrivalTerminalExit = arrivalTerminalExit;
	}
	
	/**
	 * @return the baggagePickupZone
	 */
	public MBaggagePickupZone getBaggagePickupZone() {
		return baggagePickupZone;
	}
	
	/**
	 * @param baggagePickupZone the baggagePickupZone to set
	 */
	public void setBaggagePickupZone(MBaggagePickupZone baggagePickupZone) {
		this.baggagePickupZone = baggagePickupZone;
	}

	/**
	 * @return the arrivalTerminal
	 */
	public MArrivalTerminal getArrivalTerminal() {
		return arrivalTerminal;
	}

	/**
	 * @param arrivalTerminal the arrivalTerminal to set
	 */
	public void setArrivalTerminal(MArrivalTerminal arrivalTerminal) {
		this.arrivalTerminal = arrivalTerminal;
	}

	/**
	 * @return the baggageReclaimGuichet
	 */
	public MBaggageReclaimGuichet getBaggageReclaimGuichet() {
		return baggageReclaimGuichet;
	}

	/**
	 * @param baggageReclaimGuichet the baggageReclaimGuichet to set
	 */
	public void setBaggageReclaimGuichet(MBaggageReclaimGuichet baggageReclaimGuichet) {
		this.baggageReclaimGuichet = baggageReclaimGuichet;
	}

	/**
	 * @return the bus
	 */
	public MBus getBus() {
		return bus;
	}

	/**
	 * @param bus the bus to set
	 */
	public void setBus(MBus bus) {
		this.bus = bus;
	}

	/**
	 * @return the tempBaggageStorage
	 */
	public MTempBaggageStorage getTempBaggageStorage() {
		return tempBaggageStorage;
	}

	/**
	 * @param tempBaggageStorage the tempBaggageStorage to set
	 */
	public void setTempBaggageStorage(MTempBaggageStorage tempBaggageStorage) {
		this.tempBaggageStorage = tempBaggageStorage;
	}

}
