package stanford.course3.week2

class KruskalAlgorithm {


    void find(int k, List<ClusteringEdge> graph, int nodeNumber) {
        UF uf = new UF(nodeNumber);
        def sort = graph.sort()
        def iterator = sort.iterator()
        while (iterator.hasNext() && uf.count > k) {
            ClusteringEdge e = iterator.next()
            int v = e.either;
            int w = e.other;
            if (!uf.connected(v - 1, w - 1)) {
                uf.union(v - 1, w - 1);
            }
        }

        def map = []


        int result = Integer.MIN_VALUE
        graph.each {
            edge ->
                if (!uf.connected(edge.either - 1, edge.other - 1) && result < edge.weight) {
                    result = edge.weight
                }
        }

        println(result)


        result = Integer.MAX_VALUE
        graph.each {
            edge ->
                if (!uf.connected(edge.either - 1, edge.other - 1) && result > edge.weight) {
                    result = edge.weight
                }
        }

        println(result)
    }
}


class UF {

    private int[] parent;
    private int[] rank;
    private int count;

    UF(int n) {
        count = n;
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }


    public int getCount() {
        return count;
    }

    int find(int p) {
        while (p != parent[p]) {
            parent[p] = parent[parent[p]];
            p = parent[p];
        }
        return p;
    }

    boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;

        if (rank[rootP] < rank[rootQ]) parent[rootP] = rootQ;
        else if (rank[rootP] > rank[rootQ]) parent[rootQ] = rootP;
        else {
            parent[rootQ] = rootP;
            rank[rootP]++;
        }
        count--;
    }
}

class ClusteringEdge implements Comparable {
    int either
    int other
    int weight

    int either() { either }

    @Override
    int compareTo(Object o) {
        if (weight > ((ClusteringEdge) o).weight)
            return 1
        if (weight < ((ClusteringEdge) o).weight)
            return -1
        else return 0
    }

    @Override
    String toString() {
        return "e: ${either}, o: ${other}, w: ${weight}"
    }

    int other(int v) {
        other
    }

    double weight() {
        weight
    }
}

List<ClusteringEdge> edges = []
Scanner scanner = new Scanner(new File("clustering1.txt"));
def nodesNumber = scanner.nextInt()
while (scanner.hasNext()) {

    def edge = new ClusteringEdge()
    edge.either = scanner.nextInt()
    edge.other = scanner.nextInt()
    edge.weight = scanner.nextInt()
    edges.add(edge)
}

new KruskalAlgorithm().find(4, edges, nodesNumber)
