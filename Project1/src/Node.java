import java.util.Random;

/**
 * Node stuffs
 * 
 * @author Paul Degnan, Nickolas Mitchell
 *
 */
public class Node {
	private Node parent;
	private Node left;
	private Node right;
	private Interval interval;
	private int imax;
	private int priority;
	private int height;

	public Node() {
		// Default Constructor
	}

	public Node(Node parent, Node left, Node right, Interval interval, int imax, int priority) {
		this.parent = parent;
		this.left = left;
		this.right = right;
		this.interval = interval;
		this.imax = imax;
		this.priority = priority;
	}

	/**
	 * Node (Interval i): Constructor that takes an Interval object i as its
	 * parameter. The constructor must generate a priority for the node. Therefore,
	 * after creation of a Node object, getPriority() (defined below) must return
	 * the priority of this node.
	 */

	public Node(Interval i) {
		this.interval = i;
		Random rand = new Random();
		int temp = rand.nextInt();
		if (temp < 0)
			temp *= -1;
		this.priority = temp;
		parent = null;
		left = null;
		right = null;
	}

	public Node getParent() {
		if (parent == null) {
			return null;
		} else {
			return this.parent;
		}
	}

	public void setParent(Node z) {
		this.parent = z;
	}

	public Node getLeft() {
		if (left == null) {
			return null;
		} else {
			return this.left;
		}
	}

	public void setLeft(Node z) {
		this.left = z;
	}

	public Node getRight() {
		if (right == null) {
			return null;
		} else {
			return this.right;
		}
	}

	public void setRight(Node z) {
		this.right = z;
	}

	public Interval getInterv() {
		return this.interval;
	}

	public void setIMax(int imax) {
		this.imax = imax;
	}

	public int getIMax() {
		return this.imax;
		// return interval.getHigh();
	}

	public int getPriority() {
		return this.priority;
	}

	public int getHeight() {
		return this.height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public String toString() {
		return "Interval: [" + this.getInterv().getLow() + "," + this.getInterv().getHigh() + "]     " + "Imax: "
				+ this.getIMax();

	}
}