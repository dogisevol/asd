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


    void test(int[] data) {

    }


}

int hammingDistance(int x, int y) {
    int dist = 0;
    def val = x ^ y;

    while (val != 0) {
        dist++;
        val &= val - 1;
    }

    return dist;
}

class UFHashMap {

    def parent;
    def rank;
    int count;

    UFHashMap(int n) {
        count = n
        parent = [:]
        rank = [:]
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

    void init(int item) {
        rank[item] = 0
        parent[item] = item
    }

    void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) {
            return
        }

        if (rank[rootP] < rank[rootQ]) parent[rootP] = rootQ;
        else if (rank[rootP] > rank[rootQ]) parent[rootQ] = rootP;
        else {
            parent[rootQ] = rootP;
            rank[rootP]++;
        }
        count--;
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


int[] getBitPermutations(int size, int number) {
    def val = 1
    int[] result = new int[size]
    for (int i = 0; i < size; i++) {
        result[i] = number ^ val
        val = val << 1
    }
    result
}

scanner = new Scanner(new File("clustering_big.txt"));
def n = scanner.nextInt()
size = scanner.nextInt()
def uf = new UFHashMap(n)
boolean[] array = new boolean[2.power(size)]
def c = 0
while (scanner.hasNext()) {
    if (c++ % 100 == 0) print "."
    if (c % 10000 == 0) println c
    def s = "";
    (1..size).each {
        s += scanner.nextInt()
    }
    def item = Integer.parseUnsignedInt(s, 2)
    if (array[item] == true) {
        uf.count--
        continue
    }
    uf.init(item)
    array[item] = true

    getBitPermutations(size, item).each {
        variant1 ->
            getBitPermutations(size, variant1).each {
                variant2 ->
                    if (array[variant2] && !uf.connected(item, variant2)) {
                        uf.union(item, variant2)
                    }
            }
            if (array[variant1] && !uf.connected(item, variant1)) {
                uf.union(item, variant1)
            }
    }
}

//array.eachWithIndex { e, i ->
//    if (i % 10 == 0) print "."
//    if (i % 1000 == 0) println i
//    map.get(e).each { o ->
//        if (array.contains(o)) {
//            uf.union(e, o)
//        }
//    }
//}

println uf.count


