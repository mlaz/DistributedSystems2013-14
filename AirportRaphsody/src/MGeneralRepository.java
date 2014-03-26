import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 *
 */
public class MGeneralRepository implements IPassengerGenRep, IDriverGenRep, IPorterGenRep{
	
	private int numPassengers;
	private int nBusSeats;
	private MArrivalTerminal arrivalTerminal;
	private MArrivalTerminalExit arrivalTerminalExit;
	private MBaggagePickupZone baggagePickupZone;
	private MBaggageReclaimGuichet baggageReclaimGuichet;
	private MBus bus;
	private MTempBaggageStorage tempBaggageStorage;
	private MDepartureTerminalEntrace departureTerminalEntrace;
    
    private FlightInfo plane;
    private PorterInfo porter;
    private DriverInfo driver;
    private PassengerInfo[] passengers;
    private int registeredPassengers;
    private boolean allPassReg;
   
    BufferedWriter bw;
        
	public MGeneralRepository(int numPassengers, int nBusSeats, String path) {
		passengers = null; //new PassengerInfo[numPassengers];
        registeredPassengers = 0;
        plane = null;
        this.numPassengers = numPassengers;
        this.nBusSeats = nBusSeats;
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
		
		printHeader();
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
        
        private void printLogEntry() {
            String s = "";
            
            s += (allPassReg) ? plane.toString() : "NLANDED ";
            s += porter.toString();
            s += driver.toString();
            s += '\n';
            
            if (allPassReg) //this avoids nullpointerexceptions during non-passenger threads state updates between flights
            	for(int i=0 ; i<passengers.length ; i++) 
            		s += passengers[i].toString();
            
            try {
				bw.write(s + "\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        /* porter */
        public synchronized void registerPorter() {
            porter = new PorterInfo();
        }
        
        public synchronized void updatePorterState(TPorter.states newState) {
            porter.setStat(newState);
            printLogEntry();
        }
        
        public synchronized void incLuggageAtCB() {
            porter.addconvBeltItem();
            printLogEntry();
        }
        
        public synchronized void incLuggageAtSR() {
            porter.addStoredBaggage();
            printLogEntry();
        }
        
        public synchronized void removeLuggageAtPlane() {
            plane.removeABag();
            printLogEntry();
        }
        
        /* driver */
        public synchronized void registerDriver() {
            driver = new DriverInfo(numPassengers, nBusSeats);
            //addLogEntry();
        }
        
        public synchronized void updateDriverState(TDriver.states newState) {
            driver.setStat(newState);
            printLogEntry();
        }
        
        public synchronized void updateDriverQueue(Object[] queue) {
        	int[] iqueue = new int[numPassengers];
        	int i = 0;
        	while (i < queue.length) {
        		iqueue[i] = ((Integer)queue[i]).intValue();
        		i++;
        	}
        	while (i < numPassengers) {
        		iqueue[i] = -1;
        		i++;
        	}
        	
            driver.setQueueIDs(iqueue);
            printLogEntry();
        }
        
        public synchronized void updateDriverSeats(int[] seats) {
            driver.setSeatsIDs(seats);
            printLogEntry();
        }
        
        /* passengers */
        public synchronized void registerPassenger(int pID, int planeId, boolean inTransit, int startingLuggage) {
            
            if (plane != null){
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
            
            passengers[registeredPassengers] = new PassengerInfo(pID, inTransit, startingLuggage);
            registeredPassengers++;
            plane.addBaggage(startingLuggage);
            allPassReg = (registeredPassengers == numPassengers);
        }
        
        public synchronized void setPassengerStat(int pID, TPassenger.states newStat) {
            PassengerInfo p = findPassenger(pID);
            p.setStat(newStat);
            printLogEntry();
        }
        
        public synchronized void gotLuggage(int pID) {
            PassengerInfo p = findPassenger(pID);
            p.gotLuggage();
            printLogEntry();
        }
        
        private PassengerInfo findPassenger(int pID) {
            for(PassengerInfo p:passengers) {
                if( p.getId() == pID )
                    return p;
            }
            
            return null;
        }
        
        
        ///////////////////////////////////////////
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

	/**
	 * @return the departureTerminalEntrace
	 */
	public MDepartureTerminalEntrace getDepartureTerminalEntrace() {
		return departureTerminalEntrace;
	}

	/**
	 * @param departureTerminalEntrace the departureTerminalEntrace to set
	 */
	public void setDepartureTerminalEntrace(MDepartureTerminalEntrace departureTerminalEntrace) {
		this.departureTerminalEntrace = departureTerminalEntrace;
	}

}
