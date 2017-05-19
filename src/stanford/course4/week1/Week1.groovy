package stanford.course4.week1


class Edge {
    int src, dest, weight;

    Edge() {
        src = dest = weight = 0;
    }
}

;


class Graph {
    int V, E;
    Set vertices = new HashSet()
    Edge[] edges
}

int BellmanFord(Graph graph, int src) {
    int V = graph.V, E = graph.E;
    int[] dist = new int[E];
    int shortest = Integer.MAX_VALUE


    for (int i = 0; i < V; ++i)
        dist[i] = Integer.MAX_VALUE;
    dist[src] = 0;


    for (int i = 1; i < V; ++i) {
        for (int j = 0; j < E; ++j) {
            int u = graph.edges[j].src;
            int v = graph.edges[j].dest;
            int weight = graph.edges[j].weight;
            if (dist[u] != Integer.MAX_VALUE &&
                    dist[u] + weight < dist[v])
                dist[v] = dist[u] + weight;
            if (shortest > dist[v]) {
                shortest = dist[v]
            }
        }
    }


    for (int j = 0; j < E; ++j) {
        int u = graph.edges[j].src;
        int v = graph.edges[j].dest;
        int weight = graph.edges[j].weight;
        if (dist[u] != Integer.MAX_VALUE &&
                dist[u] + weight < dist[v])
            System.out.println("Graph contains negative weight cycle");
        return Integer.MAX_VALUE
    }
    print
    return shortest
}

Graph getGraph(String fileName) {
    Scanner scanner = new Scanner(new File(fileName))
    Graph graph = new Graph()
    graph.V = scanner.nextInt()
    graph.E = scanner.nextInt()
    graph.edges = new Edge[graph.E]
    def c = 0
    while (scanner.hasNext()) {
        Edge edge = new Edge()
        edge.src = scanner.nextInt()
        edge.dest = scanner.nextInt()
        edge.weight = scanner.nextInt()
        graph.edges[c++] = edge
        graph.vertices.add(edge.src)
        graph.vertices.add(edge.dest)
    }
    return graph
}

Graph graph = getGraph("test1")

def iterator = graph.vertices.iterator()
int result = 0
while (iterator.hasNext() && result < Integer.MAX_VALUE) {
    int r = BellmanFord(graph, iterator.next())
    if (result > 0 || r == Integer.MAX_VALUE) {
        result = r
    }
}
println result

//int[][][] getArray(String fileName) {
//    Scanner scanner = new Scanner(new File(fileName))
//    def V = scanner.nextInt() + 1
//    def E = scanner.nextInt()
//    int[][][] A = new int[V][V][V]
//    for (int i = 0; i < V; i++) {
//        for (int j = 0; j < V; j++) {
//            if (i == j)
//                A[i][j][0] = 0
//            else
//                A[i][j][0] = Integer.MAX_VALUE
//        }
//    }
//    while (scanner.hasNext()) {
//        A[scanner.nextInt()][scanner.nextInt()][0] = scanner.nextInt()
//    }
//
//    for (int k = 1; k < V; k++) {
//        for (int i = 1; i < V; i++) {
//            for (int j = 1; j < V; j++) {
//                A[i][j][k] = Math.min(A[i][j][k - 1], A[i][k][k - 1] + A[k][j][k - 1])
//            }
//        }
//    }
//
//}
//
//def arr = getArray("test1")
//testNegative(arr)
//arr = getArray("g2")
//testNegative(arr)
//arr = getArray("g3")
//testNegative(arr)
//
//private void testNegative(int[][][] arr) {
//    def n = arr.length - 1
//    boolean hasNegativeChile = false
//    for (int i = 0; i <= n; i++) {
//        for (int j = 0; j <= n; j++) {
//            if (arr[i][j][n] < 0) {
//                hasNegativeChile = true
//                break
//            }
//            if (hasNegativeChile) break
//        }
//    }
//
//    println(hasNegativeChile)
//}
//
