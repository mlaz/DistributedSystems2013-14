package Utils;

import java.io.Serializable;

/**
 * Implements a VectorialClock
 * 
 * @author Filipe Teixeira <fmteixeira@ua.pt>
 * @author Miguel Azevedo <lobaoazevedo@ua.pt>
 */

public class VectorClock implements Comparable<VectorClock>, Serializable {
	private static final long serialVersionUID = 3963949565814453628L;
	
	/**
	 * Stores the vectorial clock.
	 */
	private int[] clock;
	
	/**
	 * Instanciates a VectorClock object.
	 * 
	 * @param size Number of entities that use the clock.
	 */
	public VectorClock(int size) {
		this.clock = new int[size];
	}
	
	
	/**
	 * Increments one position of the VectorClock.
	 * @param id The position to be incremented.
	 * @throws ArrayIndexOutOfBoundsException Thrown if id is negative or larger than size.
	 */
	public void increment(int id) throws ArrayIndexOutOfBoundsException {
		clock[id]++;
	}
	
	/**
	 * Returns an array of integers with the values of the clock.
	 * @return An array of integers with the values of the clock.
	 */
	public int[] getClock() {
		return clock;
	}
	
	/**
	 * Returns the size of the clock
	 * @return The size of the clock
	 */
	public int getClockSize() {
		return clock.length;
	}
	
	/**
	 * Updates this Clock with the information of an external clock.
	 * @param external Clock used to update this clock
	 */
	public void updateClock(VectorClock external) {
		if( this.getClockSize() != external.getClockSize() )
			throw new IllegalArgumentException();
		
		int[] ext = external.getClock();
		for(int i=0 ; i<this.getClockSize() ; i++ ) {
			if( clock[i] < ext[i]) {
				clock[i] = ext[i];
			}
		}
	}

	@Override
	public int compareTo(VectorClock other) {
		int sumThis  = sum( this.getClock() );
		int sumOther = sum( other.getClock() );
		
		return sumThis - sumOther;
	}
	
	/**
	 * Sums an integer array
	 * @param array Array to be summed
	 * @return The sum of the array
	 */
	private int sum(int[] array) {
		int sum = 0;
		for(int i:array) {
			sum+=i;
		}
		return sum;
	}
}