package graph.dijkstra

import graph.Edge


class DijkstraHeapAlgorithm {

    void dijkstra(List<Node> graph) {
        graph.get(0).distance = 0
        def queue = new PriorityQueue();
        queue.offer(graph.get(0))

        while (!queue.isEmpty()) {
            Node u = queue.poll()
            for (edge in u.edges) {
                Node v = graph.get(edge.dest - 1)
                def alt = u.distance + edge.weight
                if (alt < v.distance) {
                    v.distance = alt
                    queue.offer(v)
                }
            }
        }
    }

}

class Edge {
    int dest
    int weight
}

class Node implements Comparable {
    int v = -1
    List<Edge> edges = new LinkedList<>()
    int distance = 1000000

    @Override
    int compareTo(Object o) {
        return ((Node) o).distance - distance
    }
}

List<Node> graph = new LinkedList<Node>()
new File("dijkstraData.txt").eachLine { line ->
    String[] split = line.split("\t")
    Node node = new Node()
    graph.add(node)
    split.each { s ->
        if (node.v < 0)
            node.v = Integer.valueOf(s.trim()).intValue()
        else {
            def tuple = s.split(",")
            def edge = new Edge()
            edge.dest = Integer.valueOf(tuple[0].trim()).intValue()
            edge.weight = Integer.valueOf(tuple[1].trim()).intValue()
            node.edges.add(edge)

        }
    }
}
DijkstraHeapAlgorithm dij = new DijkstraHeapAlgorithm();
dij.dijkstra(graph)

[7, 37, 59, 82, 99, 115, 133, 165, 188, 197].each { v ->
    print("${graph.get(v - 1).distance},")
}
//println(graph.get(3).distance)

