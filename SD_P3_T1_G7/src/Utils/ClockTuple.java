package Utils;

import java.io.Serializable;

public class ClockTuple<T> implements Comparable<ClockTuple>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -893972544712953603L;
	
	private T data;
	private VectorClock clock;
	
	public ClockTuple(T data, VectorClock clock) {
		this.data = data;
		this.clock = clock;
	}

	public T getData() {
		return data;
	}

	public VectorClock getClock() {
		return clock;
	}

	@Override
	public int compareTo(ClockTuple other) {
		return clock.compareTo(other.getClock());
	}
}
