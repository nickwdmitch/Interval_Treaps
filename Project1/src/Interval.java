/**
 * Interval stuffs
 * 
 * @author Paul Degnan, Nickolas Mitchell
 *
 */
public class Interval {
	private int high;
	private int low;

	public Interval() {
		this.high = 0;
		this.low = 0;
	}

	public Interval(int low, int high) {
		this.high = high;
		this.low = low;
	}

	public int getHigh() {
		return high;
	}

	public void setHigh(int high) {
		this.high = high;
	}

	public int getLow() {
		return low;
	}

	public void setLow(int low) {
		this.low = low;
	}

	@Override
	public String toString() {
		return "[" + low + "," + high + "]";
	}
}
