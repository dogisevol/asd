package graph.scc

import graph.Edge
import graph.Vertex

class KosarajuSCCAlgorythm1 {


    List<List<Integer>> findSCCs(List<Integer>[] graph, List<Integer>[] reverseGraph) {

        int n = graph.length;
        boolean[] used = new boolean[n];
        List<Integer> order = new LinkedList<>();
        List<Integer> list = new LinkedList<>();
        int time = 0;
        for (int i = reverseGraph.size() - 1; i > 1; i--) {
            if (!used[i]) {
                Stack stack = new Stack()
                stack.push(i)
                def u
                while (!stack.isEmpty()) {
                    u = stack.pop()
                    used[u] = true;
                    for (int v : reverseGraph[u])
                        if (!used[v])
                            stack.push(v)
                    list.add(u);
                }
            }
            order.addAll(0, list)
            list.clear()
        }

        List<List<Integer>> components = new ArrayList<>();
        Arrays.fill(used, false);
//        Collections.reverse(order);

        for (int i : order)
            if (!used[i]) {
                List<Integer> component = new ArrayList<>();
                Stack stack = new Stack()
                stack.push(i)
                while (!stack.isEmpty()) {
                    def u = stack.pop()
                    if (!used[u]) {
                        used[u] = true;
                        for (int v : graph[u]) {
                            stack.push(v)
                        }
                        component.add(u);
                    }
                }
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
            if (!used[u]) {
                used[u] = true;
                for (int v : graph[u]) {
                    stack.push(v)
                }
                res.add(u);
            }
        }
    }

}

class KosarajuSCCFinderTest1 {

    List<List<Integer>> test() {
        List<List<Integer>> g = new ArrayList<>();
        List<List<Integer>> rg = new ArrayList<>();
        new File("scc.txt").eachLine { line ->
            String[] split = line.split(" ")
            int source = Integer.valueOf(split[0].trim())
            int dest = Integer.valueOf(split[1].trim())
            int max = Math.max(source, dest);
            while (g.size() < max +1){
                g.add(new ArrayList<Integer>());
                rg.add(new ArrayList<Integer>());
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

//result.each {
//    println(it)
//}
println("${result.get(0)?.size() ?: 0},${result.get(1)?.size() ?: 0},${result.get(2)?.size() ?: 0},${result.get(3)?.size() ?: 0},${result.get(4)?.size() ?: 0}")
