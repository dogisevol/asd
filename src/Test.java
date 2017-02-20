import java.math.BigDecimal;

public class Test {

	public static void main(String[] args) {

		System.out.println(BigDecimal.ZERO.compareTo(null));

		Test test = new Test();
		test.test();

	}

	private void test() {
		LinkedList list = new LinkedList();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
		list.add(6);
		list.add(7);
		list.add(8);
		list.add(9);
		list.add(10);
		// list.removeFromTail(10);
		list.removeFromTailRecursivly(6);
		System.out.println(list);
	}

	class LinkedList {
		private Node tail;
		private Node head;

		public void add(int data) {
			Node node = new Node(data);
			if (tail == null) {
				tail = node;
				head = node;
			} else {
				tail.next = node;
				tail = node;
			}
		}

		@Override
		public String toString() {
			StringBuilder result = new StringBuilder();
			if (head == null) {
				result.append("List is empty");
			} else {
				Node node = head;
				while (node != null) {
					result.append(node.data);
					node = node.next;
					if (node != null)
						result.append(",");
				}
			}
			return result.toString();
		}

		public void removeFromTail(int n) {
			Node node1 = head;
			Node node2 = head;
			int i = 0;
			while (i < n + 1 && node1 != null) {
				node1 = node1.next;
				i++;
			}

			if (i < n - 2) {
				System.out.println("List size less than " + n);
			} else {
				while (node1 != null) {
					node1 = node1.next;
					node2 = node2.next;
				}
				node2.next = node2.next.next;
			}
		}

		public void removeFromTailRecursivly(int n) {
			if (n > 0) {
				Node nodeBefore = findnthToLastHelper(head, head, n + 1);
				if (nodeBefore == null) {
					head = head.next;
				} else {
					Node toRemove = nodeBefore.next;
					if (toRemove != null) {
						Node nodeAfter = toRemove.next;
						nodeBefore.next = nodeAfter;
					}
				}
			}
		}

		private Node findnthToLastHelper(Node head, Node end, int n) {
			if (end == null) {
				return (n > 0 ? null : head);
			} else if (n > 0) {
				return findnthToLastHelper(head, end.next, n - 1);
			} else {
				return findnthToLastHelper(head.next, end.next, 0);
			}
		}
	}

	class Node {

		Node next;
		int data;

		public Node(int data) {
			this.data = data;
		}
	}
}
