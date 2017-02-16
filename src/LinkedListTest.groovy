	class Node {
	String data;//Node data
	Node next, random;//Next and random reference

	//Node constructor
	public Node(String data)
	{
		this.data = data;
		this.next = this.random = null;
	}
}

// linked list class
class MyLinkedList
{
	Node head;
	Node tail;

	public Node add(Node node){
		if(head == null){
			head = node
			tail = node
		}else{
			tail.next = node
			tail = node;
		}
		return node
	}

	public Node add(String data){
		return add(new Node(data))
	}

	// Method to print the list.
	void print()
	{
		Node temp = head;
		while (temp != null)
		{
			Node random = temp.random;
			String randomData = (random != null)? random.data: "NULL";
			System.out.println("Data = " + temp.data +
					", Random data = "+ randomData + temp.toString());
			temp = temp.next;
		}
	}

	// Actual clone method which returns head
	// reference of cloned linked list.
	public MyLinkedList clone()
	{
		// Initialize two references, one with original
		// list's head.
		Node origCurr = this.head, cloneCurr = null;

		// Hash map which contains node to node mapping of
		// original and clone linked list.
		Map<Node, Node> map = new HashMap<Node, Node>();

		// Traverse the original list and make a copy of that
		// in the clone linked list.
		while (origCurr != null)
		{
			cloneCurr = new Node(origCurr.data);
			map.put(origCurr, cloneCurr);
			origCurr = origCurr.next;
		}

		// Adjusting the original list reference again.
		origCurr = this.head;

		// Traversal of original list again to adjust the next
		// and random references of clone list using hash map.
		while (origCurr != null)
		{
			cloneCurr = map.get(origCurr);
			cloneCurr.next = map.get(origCurr.next);
			cloneCurr.random = map.get(origCurr.random);
			origCurr = origCurr.next;
		}

		//return the head reference of the clone list.
		def result = new MyLinkedList()
		result.add(map.get(this.head))
		return result
	}
}

MyLinkedList list = new MyLinkedList()
def node1 = list.add("1-5")
def node2 = list.add("2-4")
def node3 = list.add("3-2")
def node4 = list.add("4-3")
def node5 = list.add("5-1")
node1.random = node5
node2.random = node4
node3.random = node2
node4.random = node3
node5.random = node1

list.print()
list.clone().print()
