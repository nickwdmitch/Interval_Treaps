import java.util.ArrayList;
import java.util.List;

/**
 * Treap stuffs
 * 
 * @author Paul Degnan, Nickolas Mitchell
 *
 */
public class IntervalTreap {

	private Node root;
	private int size;
	private int height;

	public IntervalTreap() {
		root = null;
		size = 0;
		height = 0;
	}

	public Node getRoot() {
		return this.root;
	}

	public int getSize() {
		return this.size;
	}

	public int getHeight() {
		this.height = root.getHeight();
		return this.height;
	}

	/**
	 * adds node z, whose interv attribute references an Interval object, to the
	 * interval treap. This operation must maintain the required interval treap
	 * properties. The expected running time of this method should be O(log n) on an
	 * n-node interval treap.
	 * 
	 * @param z
	 */
	public void intervalInsert(Node z) {
		// Case 1, tree is empty;
		size++;
		int heightCounter = 0;
		Node y = null; // used as parent later
		Node x = root; // used to go down from root
		int key = z.getInterv().getLow(); // key of new node
		z.setIMax(z.getInterv().getHigh());
		while (x != null) {
			heightCounter++;
			y = x;
			if (x.getIMax() < z.getIMax()) {
				x.setIMax(z.getIMax());
			}
			if (key < x.getInterv().getLow()) {
				x = x.getLeft();
			} else {
				x = x.getRight();
			}
		}
		if (heightCounter > height) {
			height = heightCounter;
		}
		z.setParent(y);
		if (y == null) {
			root = z;
		} else if (key < y.getInterv().getLow()) {
			y.setLeft(z);
		} else {
			y.setRight(z);
		}
		updateHeight(z);
		checkPriority(z);
	}

	/**
	 * removes node z from the interval treap. This operation must maintain the
	 * required interval treap properties. The expected running time of this method
	 * should be O(log n) on an n-node interval treap.
	 * 
	 * @param z
	 */
	public void intervalDelete(Node z) {
		size--;
		Node y = null;
		Node tempParent = z.getParent();
		Node yOriginalPosition = null;
		boolean possiblePriorityFixNeeded = false;
		if (z.getLeft() == null) {
			transplant(z, z.getRight()); // case 1
			y = z.getRight();
			yOriginalPosition = z.getRight();
		} else if (z.getRight() == null) {
			transplant(z, z.getLeft()); // case 2
			y = z.getLeft();
			yOriginalPosition = z.getLeft();
		} else {
			// case 3
			possiblePriorityFixNeeded = true;
			y = minimum(z.getRight());
			if (y.getParent() != z) {
				transplant(y, y.getRight());
				y.setRight(z.getRight());
				y.getRight().setParent(y);
			}
			transplant(z, y);
			y.setLeft(z.getLeft());
			y.getLeft().setParent(y);
			yOriginalPosition = minimum(z.getRight());
		}
		// end of normal BST deletion and start of phase 2
		// phase 2 is the update of imax, dont understand yet though
		// my attempt using the description of the phases, not sure how to make tests to
		// test it
		if (y == null) {
			updateImaxDelete(tempParent);
		} else {
			updateImaxDelete(yOriginalPosition);
		}
		// phase 3, not sure if this will work like it did in insertion but worth a shot
		if (possiblePriorityFixNeeded)
			deletetionPriorityFix(y);
	}

	/**
	 * returns a reference to a node x in the interval treap such that x.interv
	 * overlaps interval i, or null if no such element is in the treap. This method
	 * must not modify the interval treap. The expected running time of this method
	 * should be O(log n) on an n-node interval treap.
	 * 
	 * @param i
	 * @return
	 */
	public Node intervalSearch(Interval i) {
		Node x = root;
		while (x != null && (i.getLow() > x.getInterv().getHigh() || i.getHigh() < x.getInterv().getLow())) {
			if (x.getLeft() != null && x.getLeft().getIMax() >= i.getLow()) {
				x = x.getLeft();
			} else {
				x = x.getRight();
			}
		}
		return x;
	}

	/**
	 * Returns a reference to a Node object x in the treap such that x.interv.low =
	 * i.low and x.interv.high = i.high, or null if no such node exists. The
	 * expected running time of this method should be O(log n) on an n-node interval
	 * treap. (5 extra credit points)
	 * 
	 * @param i
	 * @return
	 */
	public Node intervalSearchExactly(Interval i) {
		Node y = root;
		while (y != null) {
			if (y.getInterv().getLow() == i.getLow() && y.getInterv().getHigh() == i.getHigh()) {
				return y;
			} else if (y.getInterv().getLow() < i.getLow()) {
				y = y.getLeft();
			} else {
				y = y.getRight();
			}
		}
		return null;
	}

	/**
	 * returns a list all intervals in the treap that overlap i. This method must
	 * not modify the interval treap. The expected running time of this method
	 * should be O(min(n, k log n)), where k is the number of intervals in the
	 * output list. (10 extra credit points)
	 * 
	 * @param i
	 * @return
	 */
	public List<Interval> overlappingIntervals(Interval i) {
		List<Interval> ItervList = new ArrayList<Interval>();
		//Never really worked
		return ItervList;
	}

	/**
	 * Check priority of node z and perform rotations if needed.
	 * 
	 * @param z
	 */
	public void checkPriority(Node z) {
		// Case 1: priority is fine
		if (z.getParent() == null) {
			return;
		}
		if (z.getPriority() < z.getParent().getPriority()) {
			if (z.getParent().getLeft() == z) {
				rightRotate(z.getParent());
				updateImax(z);
				updateImax(z.getRight());
			} else {
				leftRotate(z.getParent());
				updateImax(z);
				updateImax(z.getLeft());
			}
			checkPriority(z);
		} else {
			return;
		}
	}

	/**
	 * Perform a left rotation on node x.
	 * 
	 * @param x
	 */
	public void leftRotate(Node x) {
		Node y = x.getRight();
		x.setRight(y.getLeft());
		if (y.getLeft() != null) {
			y.getLeft().setParent(x);
		}
		y.setParent(x.getParent());
		if (x.getParent() == null) {
			root = y;
		} else if (x == x.getParent().getLeft()) {
			x.getParent().setLeft(y);
		} else {
			x.getParent().setRight(y);
		}
		y.setLeft(x);
		x.setParent(y);
	}

	/**
	 * Perform a right rotation on node z.
	 * 
	 * @param z
	 */
	public void rightRotate(Node y) {
		Node x = y.getLeft();
		y.setLeft(x.getRight());
		if (x.getRight() != null) {
			x.getRight().setParent(y);
		}
		x.setParent(y.getParent());
		if (y.getParent() == null) {
			root = x;
		} else if (y == y.getParent().getRight()) {
			y.getParent().setRight(x);
		} else {
			y.getParent().setLeft(x);
		}
		x.setRight(y);
		y.setParent(x);
	}

	/**
	 * Perform a binary search to find the key value or node where the key value
	 * will be a child of.
	 * 
	 * @param key
	 * @param current
	 * @return
	 */
	public Node binarySearch(Node key, Node current) {
		if (key.getIMax() == current.getIMax() || (current.getLeft() == null && current.getRight() == null))
			return current;
		if (key.getIMax() < key.getIMax())
			return binarySearch(key, current.getLeft());
		else
			return binarySearch(key, current.getRight());
	}

	/**
	 * Get the successor of a node x
	 * 
	 * @param z
	 * @return
	 */
	public Node successor(Node x) {
		if (x.getRight() != null) {
			return minimum(x.getRight());
		}
		Node y = x.getParent();
		while (y != null && x == y.getRight()) {
			x = y;
			y = y.getParent();
		}
		return y;
	}

	/**
	 * Return the minimum of a node x
	 * 
	 * @param x
	 * @return
	 */
	private Node minimum(Node x) {
		while (x.getLeft() != null) {
			x = x.getLeft();
		}
		return x;
	}

	/**
	 * Used in deletion of a normal BST
	 * 
	 * @param u
	 * @param v
	 */
	private void transplant(Node u, Node v) {
		if (u.getParent() == null) {
			root = v;
		} else if (u.getParent().getLeft() == u) {
			u.getParent().setLeft(v);
		} else {
			u.getParent().setRight(v);
		}
		if (v != null) {
			v.setParent(u.getParent());
		}
	}

	/**
	 * Inorder traversal
	 * 
	 * Prints out the inorder traversal of the interval treap. Used for testing
	 * purposes.
	 */
	public void printInorder(Node curr) {
		if (curr == null)
			return;
		printInorder(curr.getLeft());
		System.out.println("Interval: " + curr.getInterv() + " Imax: " + curr.getIMax() + " Priority: "
				+ curr.getPriority() + "\n");
		printInorder(curr.getRight());
	}

	/**
	 * Set Imax based on criteria from project description.
	 * 
	 * @param x
	 */
	private void updateImax(Node x) {
		if (x.getLeft() == null && x.getRight() == null) {
			x.setIMax(x.getInterv().getHigh());
			x.setHeight(0);
		} else if (x.getRight() == null) {
			x.setIMax(Math.max(x.getInterv().getHigh(), x.getLeft().getIMax()));
			x.setHeight(x.getLeft().getHeight() + 1);
		} else if (x.getLeft() == null) {
			x.setIMax(Math.max(x.getInterv().getHigh(), x.getRight().getIMax()));
			x.setHeight(x.getRight().getHeight() + 1);
		} else {
			if (x.getInterv().getHigh() < x.getLeft().getIMax())
				x.setIMax(Math.max(x.getLeft().getIMax(), x.getRight().getIMax()));
			else
				x.setIMax(Math.max(x.getInterv().getHigh(), x.getRight().getIMax()));
			if (x.getRight().getHeight() > x.getLeft().getHeight()) {
				x.setHeight(x.getRight().getHeight() + 1);
			} else {
				x.setHeight(x.getLeft().getHeight() + 1);
			}
		}

	}

	/**
	 * Needs to go up from the node and fix Imax. In both cases, we decrement the
	 * imax field of each node on the path, as needed. Since the expected length of
	 * this path is O(log n) in an n-node treap, the time spent in traversing the
	 * path to update imax fields is O(log n).
	 * 
	 * @param y
	 */
	private void updateImaxDelete(Node y) {
		if (y == null) {
			return;
		}
		while (y.getParent() != null) {
			updateImax(y);
			y = y.getParent();
		}
		updateImax(y);

	}

	/**
	 * As long as z is not a leaf and z.priority is greater than the lowest priority
	 * of a child of z, perform a rotation to reverse the parent-child relation
	 * between z and z's lowest priority child.
	 * 
	 * @param y
	 */
	private void deletetionPriorityFix(Node z) {
		while (z.getLeft() != null || z.getRight() != null) {
			int lowestPriority;
			int priorityLeft;
			int priorityRight;
			if (z.getLeft() != null) {
				priorityLeft = z.getLeft().getPriority();
			} else {
				priorityLeft = -1;
			}
			if (z.getRight() != null) {
				priorityRight = z.getRight().getPriority();
			} else {
				priorityRight = -1;
			}
			if (priorityLeft == -1) {
				lowestPriority = priorityRight;
			} else if (priorityRight == -1) {
				lowestPriority = priorityLeft;
			} else if (priorityLeft > priorityRight) {
				lowestPriority = priorityRight;
			} else {
				lowestPriority = priorityLeft;
			}
			if (z.getPriority() < lowestPriority) {
				return;
			} else {
				if (lowestPriority == priorityRight) {
					leftRotate(z);
				} else {
					rightRotate(z);
				}
			}
			updateImax(z);
			updateImax(z.getParent());
		}
	}

	/**
	 * Updates the height of each parent node
	 * 
	 * @param z
	 */
	private void updateHeight(Node z) {
		while (z.getParent() != null) {
			updateImax(z);
			z = z.getParent();
		}
		updateImax(z);
	}
}
