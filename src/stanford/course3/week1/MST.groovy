package stanford.course3.week1

import heap.Heap


class PrimHeapAlgorithm {

    int find(Map<Integer, Node> graph, Node start) {
        List<Node> result = []
        start.distance = 0
        def queue = new Heap();
        start.marked = true
        queue.add(start)

        while (!queue.isEmpty()) {
            Node v = queue.poll()
            v.marked = true
            println(v)
            for (edge in v.edges) {
                Node u = graph.get(edge.dest)
                if (!u.marked) {
                    if (!queue.contains(u)) {
                        u.distance = edge.weight
                        queue.offer(u)
                    } else if (u.distance > edge.weight) {
                        u.distance = edge.weight
                    }
                }
            }
            result.add(v)
        }
        def dist = 0
        result.each { v ->
//            println v.v + ": " + v.weight
            dist += v.distance
        }
        dist
    }

}

class Edge {
    int dest
    int weight
}

class Node implements Comparable {
    Node(int v) {
        this.v = v
    }
    int v
    List<Edge> edges = new LinkedList<>()
    int distance = Integer.MAX_VALUE
    boolean marked
    int weight = 0

    @Override
    int compareTo(Object o) {
        return distance - ((Node) o).distance
    }

    @Override
    String toString() {
        return "v: ${v}, distance: ${distance}"
    }
}

Map<Integer, Node> graph = [:]
Scanner scanner = new Scanner(new File("test.txt"));
def nodesNumber = scanner.nextInt()
def edgesNumber = scanner.nextInt()
(1..nodesNumber).each {
    num ->
        graph.put(num, new Node(num))
}
while (scanner.hasNext()) {

    def v = scanner.nextInt()
    def node = graph.get(v)
    def edge = new Edge()
    def dest = scanner.nextInt()
    edge.dest = dest
    def weight = scanner.nextInt()
    edge.weight = weight
    node.edges.add(edge)

    node = graph.get(dest)
    edge = new Edge()
    edge.dest = v
    edge.weight = weight
    node.edges.add(edge)
}

PrimHeapAlgorithm dij = new PrimHeapAlgorithm();
def list = []
list.add(dij.find(graph, graph.get(7)))
list.sort().each {
    println(it)
}




