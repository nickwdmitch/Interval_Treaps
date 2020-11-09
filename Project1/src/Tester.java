import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main stuffs
 * 
 * @author Paul Degnan, Nickolas Mitchell
 *
 */
public class Tester {

	private static IntervalTreap it1;
	private static ArrayList<Interval> TP;
	private static ArrayList<Interval> TN;
	private static Scanner sc;

	public static void main(String[] args) {

		Interval testInterval1 = new Interval(2, 7);
		Interval testInterval2 = new Interval(2, 7);
		Interval testInterval3 = new Interval(12, 355);
		Interval testInterval4 = new Interval(0, 234);
		Interval testInterval5 = new Interval(2, 34);
		Interval testInterval6 = new Interval(6, 56);
		Interval testInterval7 = new Interval(54, 230);

		Node node1 = new Node(testInterval1);
		Node node2 = new Node(testInterval2);
		Node node3 = new Node(testInterval3);
		Node node4 = new Node(testInterval4);
		Node node5 = new Node(testInterval5);
		Node node6 = new Node(testInterval6);
		Node node7 = new Node(testInterval7);
		it1 = new IntervalTreap();
		TP = new ArrayList<Interval>();
		TN = new ArrayList<Interval>();

//		scanConstruct("src/mini_1.txt");
//		for (Interval i : TP) {
//			assertNotNull(it1.intervalSearch(i));
//		}
//		for (Interval j : TN) {
//			assertNull(it1.intervalSearch(j));
//		}
//		testTreapStructure(it1);
		it1.intervalInsert(node1);
		it1.intervalInsert(node2);
		it1.intervalInsert(node3);
		it1.intervalInsert(node4);
		it1.intervalInsert(node5);
		it1.intervalInsert(node6);
		it1.intervalInsert(node7);

		it1.printInorder(it1.getRoot());
		System.out.println("Height:" + it1.getHeight());
		System.out.println("SearchOverlap: " + it1.intervalSearch(testInterval1));
		System.out.println("SearchOverlap: " + it1.intervalSearch(testInterval2));
		System.out.println("SearchOverlap: " + it1.intervalSearch(testInterval3) + "\n");

		it1.intervalDelete(node1);
		it1.printInorder(it1.getRoot());
		System.out.println("Root:" + it1.getRoot() + "\n");
		System.out.println("Height root :" + it1.getRoot().getHeight() + "\n");
		System.out.println("Height:" + it1.getHeight());
		it1.intervalDelete(node2);
		it1.intervalDelete(node3);
		it1.intervalDelete(node4);
		it1.intervalDelete(node5);
		System.out.println("Root:" + it1.getRoot() + "\n");
		System.out.println("Height root :" + it1.getRoot().getHeight() + "\n");
		System.out.println("Height:" + it1.getHeight());
		System.out.println("List:" + it1.overlappingIntervals(testInterval1));




//		testTreapStructure(it1);

	}

	private static void testTreapStructure(IntervalTreap it0) {
		// Do an InOrder Traversal and append the nodes into an array
		ArrayList<Node> inOrder = new ArrayList<Node>();
		inOrder(it0.getRoot(), inOrder);

		// Check if the array is sorted. If it is not sorted, it's not a valid treap.
		for (int k = 0; k < inOrder.size() - 1; k++) {
			if (inOrder.get(k).getInterv().getLow() > inOrder.get(k + 1).getInterv().getLow()) {
				fail("failed treap's BST property!");
			}
		}
	}

	private static void scanConstruct(String fn) {
		File f = new File(fn);
		String line;
		String[] split;
		try {
			sc = new Scanner(f);
			sc.nextLine(); // skip first line "TP"
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				if (line.equals("TN"))
					break;
				split = line.split(" ");
				TP.add(new Interval(Integer.parseInt(split[0]), Integer.parseInt(split[1])));
			}
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				if (line.equals("Intervals"))
					break;
				split = line.split(" ");
				TN.add(new Interval(Integer.parseInt(split[0]), Integer.parseInt(split[1])));
			}
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				split = line.split(" ");
				it1.intervalInsert(new Node(new Interval(Integer.parseInt(split[0]), Integer.parseInt(split[1]))));
			}
		} catch (FileNotFoundException e) {
			fail("File not found exception");
		}

	}

	public static void inOrder(Node node, ArrayList<Node> array) {
		if (node == null) {
			return;
		}
		inOrder(node.getLeft(), array);
		array.add(node);

		// As you visit each node, check for the heap property.
		if (node.getParent() != null && node.getPriority() < node.getParent().getPriority()) {
			fail("failed treap's min-heap property!");
		}

		inOrder(node.getRight(), array);
	}

}
