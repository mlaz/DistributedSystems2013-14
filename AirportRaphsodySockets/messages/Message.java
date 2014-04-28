package messages;

import java.io.Serializable;

public class Message  implements Serializable {
	
	/**
	 * AUTO-GENERATED
	 */
	private static final long serialVersionUID = 9026264442617704395L;
	
	/* Types - defines the EXTRA data fields the message has, BESIDES the type */
	public static final int ACK			= 0;
	public static final int INT 		= 1;
	public static final int INT_INT 	= 2;
	public static final int INT_INT_INT	= 3;
	public static final int BOOL		= 5;
	public static final int INT_BOOL	= 6;
	public static final int INT_INT_STR = 8;
	public static final int INT_STR		= 9;
	public static final int INT_INT_BOOL= 10;
	public static final int INT_INT_INT_INT_BOOL = 11;
	public static final int INT_INTARR	= 12;
	
	/******************* Actions ********************/
	public static final int WAIT_FOR_PASSENGERS				= 1;
	public static final int PARK_LET_OFF					= 2;
	public static final int TAKE_A_REST						= 3;
	public static final int WHAT_SHOULD_I_DO 				= 4;
	public static final int TRY_TO_COLLECT_BAG 				= 5;
	public static final int GET_NUM_FLIGHTS 				= 6;
	public static final int GET_NUM_PASSENGERS 				= 7;
	public static final int GET_MAX_BAGS 					= 8;
	public static final int GET_GENREP_PORT 				= 9;
	public static final int GET_GENREP_HOST 				= 10;
	public static final int SET_ARRIVAL_TERMINAL			= 12;
	public static final int GET_NUM_SEATS 					= 13;
	public static final int TAKE_A_BUS 						= 14;
	public static final int GO_HOME 						= 15;
	public static final int ANNOUNCING_BUS_BOARDING			= 16;
	public static final int ANNOUNCING_BUS_DEPARTURE		= 17;
	public static final int END_SIMULATION					= 18;
	public static final int GET_ARRIVAL_TERMINAL			= 19;
	public static final int GET_ARRIVAL_TERMINAL_EXIT		= 20;
	public static final int GET_BAGGAGE_PICKUP_ZONE			= 21;
	public static final int GET_BAGGAGE_RECLAIM_GUICHET		= 22;
	public static final int GET_BUS							= 23;
	public static final int GET_DEPARTURE_TERMINAL_ENTRANCE	= 24;
	public static final int GET_TEMP_BAGGAGE_STORAGE		= 25;
	public static final int GOT_LUGGAGE						= 26;
	public static final int INC_LUGGAGE_AT_CB				= 27;
	public static final int INC_LUGGAGE_AT_SR				= 28;
	public static final int REGISTER_DRIVER					= 29;
	public static final int REGISTER_PASSENGER				= 30;
	public static final int REGISTER_PORTER					= 31;
	public static final int REMOVE_LUGGAGE_AT_PLANE			= 32;
	public static final int SET_ARRIVAL_TERMINAL_EXIT		= 34;
	public static final int SET_BAGGAGE_PICKUP_ZONE			= 35;
	public static final int SET_BAGGAGE_RECLAIM_GUICHET		= 36;
	public static final int SET_BUS							= 37;
	public static final int SET_DEPARTURE_TERMINAL_ENTRANCE	= 38;
	public static final int SET_PASSENGER_STAT				= 39;
	public static final int SET_TEMP_BAGGAGE_STORAGE		= 40;
	public static final int UPDATE_DRIVER_QUEUE				= 41;
	public static final int UPDATE_DRIVER_SEATS				= 42;
	public static final int UPDATE_DRIVER_STATE				= 43;
	public static final int UPDATE_PORTER_STATE				= 44;
	public static final int CARRY_IT_TO_APPROPRIATE_STORAGE = 45;
	public static final int NO_MORE_BAGS_TO_COLLECT 		= 46;
	public static final int RECLAIM_BAGS 					= 47;
	public static final int GET_BUS_DEPARTURE_INTERVAL 		= 48;
	public static final int ENTER_THE_BUS 					= 49;
	public static final int LEAVE_THE_BUS 					= 50;
	public static final int PREPARE_NEXT_LEG 				= 51;
	
	/*************** Possible Objects ***************/
	private int type;
	private int int1, int2, int3, int4;
	private int[] intArr;
	private boolean bool;
	private String str;

	/***************** Constructors *****************/
	/* ACK */
	public Message ( int type ) {
		this.type = type;
	}
	
	/* INT */
	public Message (int type, int int1 ) {
		this.type = type;
		this.int1 = int1;
	}
	
	/* BOOL */
	public Message (int type, boolean bool ) {
		this.type = type;
		this.bool = bool;
	}
	/* INT BOOL */
	public Message (int type, int int1, boolean bool ) {
		this.type = type;
		this.int1 = int1;
		this.bool = bool;
	}
	public Message (int type, int int1, int[] intArr ) {
		this.type = type;
		this.int1 = int1;
		this.intArr = intArr;
	}
	/* INT INT */
	public Message (int type, int int1, int int2) {
		this.type = type;
		this.int1 = int1;
		this.int2 = int2;
	}
	/* INT INT BOOL */
	public Message (int type, int int1, int int2, boolean bool ) {
		this.type = type;
		this.int1 = int1;
		this.int2 = int2;
		this.bool = bool;
	}
	/* INT INT INT*/
	public Message (int type, int int1, int int2, int int3 ) {
		this.type = type;
		this.int1 = int1;
		this.int2 = int2;
		this.int3 = int3;
		this.int4 = int4;
	}
	/* INT_INT_INT_INT_BOOL */
	public Message (int type, int int1, int int2, int int3, int int4, boolean bool ) {
		this.type = type;
		this.int1 = int1;
		this.int2 = int2;
		this.int3 = int3;
		this.int4 = int4;
		this.bool = bool;
	}
	/* INT STR */
	public Message (int type, int int1, String str ) {
		this.type = type;
		this.int1 = int1;
		this.str  = str;
	}
	
	/* INT_INT_STR */
	public Message ( int type, int int1, int int2, String str ) {
		this.type = type;
		this.int1 = int1;
		this.int2 = int2;
		this.str  = str;
	}
	
	/******************* Getters ********************/
	public int getType() {
		return type;
	}
	
	public int getInt1() {
		return int1;
	}
	
	public int getInt2() {
		return int2;
	}
	
	public int getInt3() {
		return int3;
	}

	public int getInt4() {
		return int4;
	}
	
	public int[] getIntArr() {
		return intArr;
	}

	public String getString() {
		return str;
	}

	public boolean getBool() {
		return bool;
	}

}
