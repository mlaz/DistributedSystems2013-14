package Utils;

import java.io.Serializable;

/**
 * A data type that creates a tuple of a VectorClock and any other object.
 * 
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 *
 * @param <T> The  DataType to be paired with the VectorClock
 */
public class ClockTuple<T> implements Comparable<ClockTuple>, Serializable {
	private static final long serialVersionUID = -893972544712953603L;
	
	/**
	 *	The data. 
	 */
	private T data;
	/**
	 * The vectorial clock.
	 */
	private VectorClock clock;
	
	/**
	 * Instaciates a ClockTuple object.
	 * 
	 * @param data The data
	 * @param clock The vectorial clock
	 */
	public ClockTuple(T data, VectorClock clock) {
		this.data = data;
		this.clock = clock;
	}

	/**
	 * Returns the data
	 * @return The data
	 */
	public T getData() {
		return data;
	}

	/**
	 * Returns the vectorial clock.
	 * @return The vectorial clock.
	 */
	public VectorClock getClock() {
		return clock;
	}

	@Override
	public int compareTo(ClockTuple other) {
		return clock.compareTo(other.getClock());
	}
}
