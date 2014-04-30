package messages;

import java.io.Serializable;

/**
 *
 * @author miguel
 */
public class Message  implements Serializable {
	
	/**
	 * AUTO-GENERATED
	 */
	private static final long serialVersionUID = 9026264442617704395L;
	
	/* Types - defines the EXTRA data fields the message has, BESIDES the type */

    /**
     *
     */
    
	public static final int ACK			= 0;

    /**
     *
     */
    public static final int INT 		= 1;

    /**
     *
     */
    public static final int INT_INT 	= 2;

    /**
     *
     */
    public static final int INT_INT_INT	= 3;

    /**
     *
     */
    public static final int BOOL		= 5;

    /**
     *
     */
    public static final int INT_BOOL	= 6;

    /**
     *
     */
    public static final int INT_INT_STR = 8;

    /**
     *
     */
    public static final int INT_STR		= 9;

    /**
     *
     */
    public static final int INT_INT_BOOL= 10;

    /**
     *
     */
    public static final int INT_INT_INT_INT_BOOL = 11;

    /**
     *
     */
    public static final int INT_INTARR	= 12;
	
	/******************* Actions ********************/
	public static final int WAIT_FOR_PASSENGERS				= 1;

    /**
     *
     */
    public static final int PARK_LET_OFF					= 2;

    /**
     *
     */
    public static final int TAKE_A_REST						= 3;

    /**
     *
     */
    public static final int WHAT_SHOULD_I_DO 				= 4;

    /**
     *
     */
    public static final int TRY_TO_COLLECT_BAG 				= 5;

    /**
     *
     */
    public static final int GET_NUM_FLIGHTS 				= 6;

    /**
     *
     */
    public static final int GET_NUM_PASSENGERS 				= 7;

    /**
     *
     */
    public static final int GET_MAX_BAGS 					= 8;

    /**
     *
     */
    public static final int GET_GENREP_PORT 				= 9;

    /**
     *
     */
    public static final int GET_GENREP_HOST 				= 10;

    /**
     *
     */
    public static final int SET_ARRIVAL_TERMINAL			= 12;

    /**
     *
     */
    public static final int GET_NUM_SEATS 					= 13;

    /**
     *
     */
    public static final int TAKE_A_BUS 						= 14;

    /**
     *
     */
    public static final int GO_HOME 						= 15;

    /**
     *
     */
    public static final int ANNOUNCING_BUS_BOARDING			= 16;

    /**
     *
     */
    public static final int ANNOUNCING_BUS_DEPARTURE		= 17;

    /**
     *
     */
    public static final int END_SIMULATION					= 18;

    /**
     *
     */
    public static final int GET_ARRIVAL_TERMINAL			= 19;

    /**
     *
     */
    public static final int GET_ARRIVAL_TERMINAL_EXIT		= 20;

    /**
     *
     */
    public static final int GET_BAGGAGE_PICKUP_ZONE			= 21;

    /**
     *
     */
    public static final int GET_BAGGAGE_RECLAIM_GUICHET		= 22;

    /**
     *
     */
    public static final int GET_BUS							= 23;

    /**
     *
     */
    public static final int GET_DEPARTURE_TERMINAL_ENTRANCE	= 24;

    /**
     *
     */
    public static final int GET_TEMP_BAGGAGE_STORAGE		= 25;

    /**
     *
     */
    public static final int GOT_LUGGAGE						= 26;

    /**
     *
     */
    public static final int INC_LUGGAGE_AT_CB				= 27;

    /**
     *
     */
    public static final int INC_LUGGAGE_AT_SR				= 28;

    /**
     *
     */
    public static final int REGISTER_DRIVER					= 29;

    /**
     *
     */
    public static final int REGISTER_PASSENGER				= 30;

    /**
     *
     */
    public static final int REGISTER_PORTER					= 31;

    /**
     *
     */
    public static final int REMOVE_LUGGAGE_AT_PLANE			= 32;

    /**
     *
     */
    public static final int SET_ARRIVAL_TERMINAL_EXIT		= 34;

    /**
     *
     */
    public static final int SET_BAGGAGE_PICKUP_ZONE			= 35;

    /**
     *
     */
    public static final int SET_BAGGAGE_RECLAIM_GUICHET		= 36;

    /**
     *
     */
    public static final int SET_BUS							= 37;

    /**
     *
     */
    public static final int SET_DEPARTURE_TERMINAL_ENTRANCE	= 38;

    /**
     *
     */
    public static final int SET_PASSENGER_STAT				= 39;

    /**
     *
     */
    public static final int SET_TEMP_BAGGAGE_STORAGE		= 40;

    /**
     *
     */
    public static final int UPDATE_DRIVER_QUEUE				= 41;

    /**
     *
     */
    public static final int UPDATE_DRIVER_SEATS				= 42;

    /**
     *
     */
    public static final int UPDATE_DRIVER_STATE				= 43;

    /**
     *
     */
    public static final int UPDATE_PORTER_STATE				= 44;

    /**
     *
     */
    public static final int CARRY_IT_TO_APPROPRIATE_STORAGE = 45;

    /**
     *
     */
    public static final int NO_MORE_BAGS_TO_COLLECT 		= 46;

    /**
     *
     */
    public static final int RECLAIM_BAGS 					= 47;

    /**
     *
     */
    public static final int GET_BUS_DEPARTURE_INTERVAL 		= 48;

    /**
     *
     */
    public static final int ENTER_THE_BUS 					= 49;

    /**
     *
     */
    public static final int LEAVE_THE_BUS 					= 50;

    /**
     *
     */
    public static final int PREPARE_NEXT_LEG 				= 51;

    /**
     *
     */
    public static final int	SET_PORTER_AS_DEAD				= 52;

    /**
     *
     */
    public static final int SET_DRIVER_AS_DEAD				= 53;

    /**
     *
     */
    public static final int WAIT_FOR_PORTER_TO_DIE					= 54;

    /**
     *
     */
    public static final int WAIT_FOR_DRIVER_TO_DIE					= 55;
	
	/*************** Possible Objects ***************/
	private int type;
	private int int1, int2, int3, int4;
	private int[] intArr;
	private boolean bool;
	private String str;

	/***************** Constructors
     * @param type *****************/
	/* ACK */
	public Message ( int type ) {
		this.type = type;
	}
	
	/* INT */

    /**
     *
     * @param type
     * @param int1
     */
    
	public Message (int type, int int1 ) {
		this.type = type;
		this.int1 = int1;
	}
	
	/* BOOL */

    /**
     *
     * @param type
     * @param bool
     */
    
	public Message (int type, boolean bool ) {
		this.type = type;
		this.bool = bool;
	}
	/* INT BOOL */

    /**
     *
     * @param type
     * @param int1
     * @param bool
     */
    
	public Message (int type, int int1, boolean bool ) {
		this.type = type;
		this.int1 = int1;
		this.bool = bool;
	}

    /**
     *
     * @param type
     * @param int1
     * @param intArr
     */
    public Message (int type, int int1, int[] intArr ) {
		this.type = type;
		this.int1 = int1;
		this.intArr = intArr;
	}
	/* INT INT */

    /**
     *
     * @param type
     * @param int1
     * @param int2
     */
    
	public Message (int type, int int1, int int2) {
		this.type = type;
		this.int1 = int1;
		this.int2 = int2;
	}
	/* INT INT BOOL */

    /**
     *
     * @param type
     * @param int1
     * @param int2
     * @param bool
     */
    
	public Message (int type, int int1, int int2, boolean bool ) {
		this.type = type;
		this.int1 = int1;
		this.int2 = int2;
		this.bool = bool;
	}
	/* INT INT INT*/

    /**
     *
     * @param type
     * @param int1
     * @param int2
     * @param int3
     */
    
	public Message (int type, int int1, int int2, int int3 ) {
		this.type = type;
		this.int1 = int1;
		this.int2 = int2;
		this.int3 = int3;
	}
	/* INT_INT_INT_INT_BOOL */

    /**
     *
     * @param type
     * @param int1
     * @param int2
     * @param int3
     * @param int4
     * @param bool
     */
    
	public Message (int type, int int1, int int2, int int3, int int4, boolean bool ) {
		this.type = type;
		this.int1 = int1;
		this.int2 = int2;
		this.int3 = int3;
		this.int4 = int4;
		this.bool = bool;
	}
	/* INT STR */

    /**
     *
     * @param type
     * @param int1
     * @param str
     */
    
	public Message (int type, int int1, String str ) {
		this.type = type;
		this.int1 = int1;
		this.str  = str;
	}
	
	/* INT_INT_STR */

    /**
     *
     * @param type
     * @param int1
     * @param int2
     * @param str
     */
    
	public Message ( int type, int int1, int int2, String str ) {
		this.type = type;
		this.int1 = int1;
		this.int2 = int2;
		this.str  = str;
	}
	
	/******************* Getters
     * @return  ********************/
	public int getType() {
		return type;
	}

    /**
     *
     * @return
     */
    public int getInt1() {
		return int1;
	}

    /**
     *
     * @return
     */
    public int getInt2() {
		return int2;
	}

    /**
     *
     * @return
     */
    public int getInt3() {
		return int3;
	}

    /**
     *
     * @return
     */
    public int getInt4() {
		return int4;
	}

    /**
     *
     * @return
     */
    public int[] getIntArr() {
		return intArr;
	}

    /**
     *
     * @return
     */
    public String getString() {
		return str;
	}

    /**
     *
     * @return
     */
    public boolean getBool() {
		return bool;
	}

    /**
     *
     * @return
     */
    public String toString() {
		String msg = "MESSAGE --------\nTYPE: ";
		switch(type) {
	    	case ACK:
	    		msg += "ACK\n";
	    		break;
	        case INT:
	        	msg += "INT\n";
	        	msg += "    INT1: "+this.getInt1() + "    if action = ";
	        	msg += addAction(this.getInt1()) + '\n';
	        	break;
	        case INT_INT:
	        	msg += "INT_INT\n";
	        	msg += "    INT1: "+this.getInt1() + "    if action = ";
	        	msg += addAction(this.getInt1()) + '\n';
	        	msg += "    INT2: "+this.getInt2() + '\n';
	        	break;
	        case INT_INT_INT:
	        	msg += "INT_INT_INT\n";
	        	msg += "    INT1: "+this.getInt1() + "    if action = ";
	        	msg += addAction(this.getInt1()) + '\n';
	        	msg += "    INT2: "+this.getInt2() + '\n';
	        	msg += "    INT3: "+this.getInt3() + '\n';
	        	break;
	        case BOOL:
	        	msg += "BOOL\n";
	        	msg += "    BOOL: "+this.getBool() + '\n';
	        	break;
	        case INT_BOOL:
	        	msg += "INT_BOOL\n";
	        	msg += "    INT1: "+this.getInt1() + "    if action = ";
	        	msg += addAction(this.getInt1()) + '\n';
	        	msg += "    BOOL: "+this.getBool() + '\n';
	        	break;
	        case INT_INT_STR:
	        	msg += "INT_INT_STR\n";
	        	msg += "    INT1: "+this.getInt1() + "    if action = ";
	        	msg += addAction(this.getInt1()) + '\n';
	        	msg += "    INT2: "+this.getInt2() + '\n';
	        	msg += "    STR : "+this.getString() + '\n';
	        	break;
	        case INT_STR:
	        	msg += "INT_STR\n";
	        	msg += "    INT1: "+this.getInt1() + "    if action = ";
	        	msg += addAction(this.getInt1()) + '\n';
	        	msg += "    STR : "+this.getString() + '\n';
	        	break;
	        case INT_INT_BOOL:
	        	msg += "INT_INT_BOOL\n";
	        	msg += "    INT1: "+this.getInt1() + "    if action = ";
	        	msg += addAction(this.getInt1()) + '\n';
	        	msg += "    INT2: "+this.getInt2() + '\n';
	        	msg += "    BOOL: "+this.getBool() + '\n';
	        	break;
	        case INT_INT_INT_INT_BOOL:
	        	msg += "INT_INT_INT_INT_BOOL\n";
	        	msg += "    INT1: "+this.getInt1() + "    if action = ";
	        	msg += addAction(this.getInt1()) + '\n';
	        	msg += "    INT2: "+this.getInt2() + '\n';
	        	msg += "    INT3: "+this.getInt3() + '\n';
	        	msg += "    INT4: "+this.getInt4() + '\n';
	        	msg += "    BOOL: "+this.getBool() + '\n';
	        	break;
	        case INT_INTARR:
	        	msg += "INT_INTARR\n";
	        	msg += "    INT1: "+this.getInt1() + "    if action = ";
	        	msg += addAction(this.getInt1()) + '\n';
	        	msg += "    INTA: [";
	        	for(int i=0 ; i<intArr.length ; i++) {
	        		msg += intArr[i] + "  ";
	        	}
	        	msg += "]" + '\n';
	        	break;
		}
		
		return msg;
	}
	
	private String addAction(int action) {
		switch( action ) {
			case WAIT_FOR_PASSENGERS:
			    return ("WAIT_FOR_PASSENGERS");
			case PARK_LET_OFF:
			    return ("PARK_LET_OFF");
			case TAKE_A_REST:
			    return ("TAKE_A_REST");
			case WHAT_SHOULD_I_DO:
			    return ("WHAT_SHOULD_I_DO");
			case TRY_TO_COLLECT_BAG:
			    return ("TRY_TO_COLLECT_BAG");
			case GET_NUM_FLIGHTS:
			    return ("GET_NUM_FLIGHTS");
			case GET_NUM_PASSENGERS:
			    return ("GET_NUM_PASSENGERS");
			case GET_MAX_BAGS:
			    return ("GET_MAX_BAGS");
			case GET_GENREP_PORT:
			    return ("GET_GENREP_PORT");
			case GET_GENREP_HOST:
			    return ("GET_GENREP_HOST");
			case SET_ARRIVAL_TERMINAL:
			    return ("SET_ARRIVAL_TERMINAL");
			case GET_NUM_SEATS:
			    return ("GET_NUM_SEATS");
			case TAKE_A_BUS:
			    return ("TAKE_A_BUS");
			case GO_HOME:
			    return ("GO_HOME");
			case ANNOUNCING_BUS_BOARDING:
			    return ("ANNOUNCING_BUS_BOARDING");
			case ANNOUNCING_BUS_DEPARTURE:
			    return ("ANNOUNCING_BUS_DEPARTURE");
			case END_SIMULATION:
			    return ("END_SIMULATION");
			case GET_ARRIVAL_TERMINAL:
			    return ("GET_ARRIVAL_TERMINAL");
			case GET_ARRIVAL_TERMINAL_EXIT:
			    return ("GET_ARRIVAL_TERMINAL_EXIT");
			case GET_BAGGAGE_PICKUP_ZONE:
			    return ("GET_BAGGAGE_PICKUP_ZONE");
			case GET_BAGGAGE_RECLAIM_GUICHET:
			    return ("GET_BAGGAGE_RECLAIM_GUICHET");
			case GET_BUS:
			    return ("GET_BUS");
			case GET_DEPARTURE_TERMINAL_ENTRANCE:
			    return ("GET_DEPARTURE_TERMINAL_ENTRANCE");
			case GET_TEMP_BAGGAGE_STORAGE:
			    return ("GET_TEMP_BAGGAGE_STORAGE");
			case GOT_LUGGAGE:
			    return ("GOT_LUGGAGE");
			case INC_LUGGAGE_AT_CB:
			    return ("INC_LUGGAGE_AT_CB");
			case INC_LUGGAGE_AT_SR:
			    return ("INC_LUGGAGE_AT_SR");
			case REGISTER_DRIVER:
			    return ("REGISTER_DRIVER");
			case REGISTER_PASSENGER:
			    return ("REGISTER_PASSENGER");
			case REGISTER_PORTER:
			    return ("REGISTER_PORTER");
			case REMOVE_LUGGAGE_AT_PLANE:
			    return ("REMOVE_LUGGAGE_AT_PLANE");
			case SET_ARRIVAL_TERMINAL_EXIT:
			    return ("SET_ARRIVAL_TERMINAL_EXIT");
			case SET_BAGGAGE_PICKUP_ZONE:
			    return ("SET_BAGGAGE_PICKUP_ZONE");
			case SET_BAGGAGE_RECLAIM_GUICHET:
			    return ("SET_BAGGAGE_RECLAIM_GUICHET");
			case SET_BUS:
			    return ("SET_BUS");
			case SET_DEPARTURE_TERMINAL_ENTRANCE:
			    return ("SET_DEPARTURE_TERMINAL_ENTRANCE");
			case SET_PASSENGER_STAT:
			    return ("SET_PASSENGER_STAT");
			case SET_TEMP_BAGGAGE_STORAGE:
			    return ("SET_TEMP_BAGGAGE_STORAGE");
			case UPDATE_DRIVER_QUEUE:
			    return ("UPDATE_DRIVER_QUEUE");
			case UPDATE_DRIVER_SEATS:
			    return ("UPDATE_DRIVER_SEATS");
			case UPDATE_DRIVER_STATE:
			    return ("UPDATE_DRIVER_STATE");
			case UPDATE_PORTER_STATE:
			    return ("UPDATE_PORTER_STATE");
			case CARRY_IT_TO_APPROPRIATE_STORAGE:
			    return ("CARRY_IT_TO_APPROPRIATE_STORAGE");
			case NO_MORE_BAGS_TO_COLLECT:
			    return ("NO_MORE_BAGS_TO_COLLECT");
			case RECLAIM_BAGS:
			    return ("RECLAIM_BAGS");
			case GET_BUS_DEPARTURE_INTERVAL:
			    return ("GET_BUS_DEPARTURE_INTERVAL");
			case ENTER_THE_BUS:
			    return ("ENTER_THE_BUS");
			case LEAVE_THE_BUS:
			    return ("LEAVE_THE_BUS");
			case PREPARE_NEXT_LEG:
			    return ("PREPARE_NEXT_LEG");
			case SET_PORTER_AS_DEAD:
				return ("SET_PORTER_AS_DEAD:");
			case WAIT_FOR_PORTER_TO_DIE:
				return ("WAIT_FOR_PORTER_TO_DIE");
			case SET_DRIVER_AS_DEAD:
				return ("SET_DRIVER_AS_DEAD");
			case WAIT_FOR_DRIVER_TO_DIE:
				return ("WAIT_FOR_DRIVER_TO_DIE");
			default:
				return ("UNKOWN ACTION");
		}
	}

    /**
     *
     */
    public void print() {
		System.out.println(this.toString());
	}
}
