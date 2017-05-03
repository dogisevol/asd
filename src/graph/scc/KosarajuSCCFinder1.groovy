package graph.scc

import graph.Edge
import graph.Vertex

class KosarajuSCCAlgorythm1 {


    List<List<Integer>> findSCCs(List<Integer>[] graph, List<Integer>[] reverseGraph) {

        int n = graph.length;
        boolean[] used = new boolean[n];
        List<Integer> order = new ArrayList<>();
        for (int i = 1; i < n; i++)
            if (!used[i]) {
                Stack stack = new Stack()
                stack.push(i)
                dfs(graph, used, order, stack);
            }

        List<List<Integer>> components = new ArrayList<>();
        Arrays.fill(used, false);
        Collections.reverse(order);

        for (int u : order)
            if (!used[u]) {
                List<Integer> component = new ArrayList<>();
                Stack stack = new Stack()
                stack.push(u)
                dfs(reverseGraph, used, component, stack);
                components.add(component);
            }



        return components;
    }

    static void dfs(List<Integer>[] graph, boolean[] used, List<Integer> res, int u) {
        used[u] = true;
        for (int v : graph[u])
            if (!used[v])
                dfs(graph, used, res, v);
        res.add(u);
    }

    static void dfs(List<Integer>[] graph, boolean[] used, List<Integer> res, Stack stack) {
        while (!stack.isEmpty()) {
            def u = stack.pop()
            used[u] = true;
            for (int v : graph[u])
                if (!used[v])
                    stack.push(v)
            res.add(u);
        }
    }

}

class KosarajuSCCFinderTest1 {

    List<List<Integer>> test() {
        List<List<Integer>> g = new ArrayList<>();
        List<List<Integer>> rg = new ArrayList<>();
        File test = new File("scc.txt");
        new File("input.txt").eachLine { line ->
            String[] split = line.split(" ")
            int source = Integer.valueOf(split[0].trim())
            int dest = Integer.valueOf(split[1].trim())
            if (g[source] == null) {
                g[source] = new ArrayList<>();
            }
            g[source].add(dest)

            if (rg[dest] == null) {
                rg[dest] = new ArrayList<>();
            }
            rg[dest].add(source)
        }
        return new KosarajuSCCAlgorythm1().findSCCs(g.toArray(new List<Integer>[g.size()]), rg.toArray(new List<Integer>[rg.size()]))
    }

}

List<List<Integer>> result = new KosarajuSCCFinderTest1().test()
result.sort(new Comparator() {
    @Override
    int compare(Object o1, Object o2) {
        return ((List) o2).size() - ((List) o1).size()
    }
})

result.each {
    println(it)
}
//println("${result.get(0)?.size() ?: 0},${result.get(1)?.size() ?: 0},${result.get(2)?.size() ?: 0},${result.get(3)?.size() ?: 0},${result.get(4)?.size() ?: 0}")
