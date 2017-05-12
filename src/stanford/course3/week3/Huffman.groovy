class TrieNode implements Comparable {
    TrieNode(int id, int weight) {
        this.id = id
        this.weight = weight
        this.isLeaf = true
    }

    TrieNode(TrieNode n1, TrieNode n2) {
        id = 0
        this.weight = n1.weight + n2.weight
        if (n1.weight > n2.weight) {
            left = n2
            right = n1
        } else {
            left = n1
            right = n2
        }
    }
    int id
    int weight
    TrieNode left
    TrieNode right
    boolean isLeaf = false

    @Override
    int compareTo(Object o) {
        TrieNode other = o
        if (this.weight > other.weight)
            return 1
        if (this.weight < other.weight)
            return -1
        return 0
    }

    @Override
    String toString() {
        return this.id + ": " + this.weight
    }
}

PriorityQueue<TrieNode> queue = new PriorityQueue()

Scanner scanner = new Scanner(new File("test.txt"))
def c = 0;
scanner.nextInt()
while (scanner.hasNext()) {
    def next = scanner.nextInt()
    queue.offer(new TrieNode(++c, next))
}

while (queue.size() > 1) {
    queue.offer(new TrieNode(queue.poll(), queue.poll()))
}

Stack<TrieNode> stack = new Stack<>()
def root = queue.poll()

println(maxDepth(root))
println(minDepth(root))

int maxDepth(TrieNode node) {
    if (node == null)
        return -1
    int lDepth = maxDepth(node.left)
    int rDepth = maxDepth(node.right)
    if (lDepth > rDepth)
        return (lDepth + 1)
    else return (rDepth + 1)
}

int minDepth(TrieNode node) {
    if (node == null)
        return -1
    int lDepth = minDepth(node.left)
    int rDepth = minDepth(node.right)
    if (lDepth < rDepth)
        return (lDepth + 1)
    else return (rDepth + 1)
}





