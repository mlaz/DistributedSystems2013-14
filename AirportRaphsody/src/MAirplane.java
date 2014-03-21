import java.util.Stack;

/**
 * 
 */

/**
 * @author miguel
 *
 */
public class MAirplane {
	private Stack<Bag> planesHold;
	
	public MAirplane (Stack<Bag> bags) {
		this.planesHold = bags;
	}
	
	public synchronized Bag tryToCollectABag () {
		if (planesHold.isEmpty())
			return null;
		
		return planesHold.pop();
	}
}
