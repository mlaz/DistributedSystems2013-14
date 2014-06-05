package Utils;

public class ClockTuple<T> implements Comparable {
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
	public int compareTo(Object o) {
		return clock.compareTo(o);
	}
}
