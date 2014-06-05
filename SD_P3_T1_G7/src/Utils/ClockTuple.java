package Utils;

public class ClockTuple<T> {
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
}
