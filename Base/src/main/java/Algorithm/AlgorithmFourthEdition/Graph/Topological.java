package Algorithm.AlgorithmFourthEdition.Graph;

/**
 * Created by user-hfc on 2020/6/19.
 *
 * 拓扑排序
 */
public class Topological {

    private Iterable<Integer> order;    // 顶点的拓扑排序

    public Topological(Digraph G) {
        DirectedCycle cycleFinder = new DirectedCycle(G);
        if (!cycleFinder.hasCycle()) {
            DepthFirstOrder dfs = new DepthFirstOrder(G);
            order = dfs.reversePost();
        }
    }

    public Topological(EdgeWeightedDigraph G) {
        DirectedCycle cycleFinder = new DirectedCycle(G);
        if (!cycleFinder.hasCycle()) {
            DepthFirstOrder dfs = new DepthFirstOrder(G);
            order = dfs.reversePost();
        }
    }

    /**
     * 拓扑有序的所有顶点
     */
    public Iterable<Integer> order() {
        return order;
    }

    /**
     * 是否是有向无环图
     */
    public boolean isDAG() {
        return order != null;
    }

    public static void main(String[] args) {
        String path = System.getProperty("user.dir") + "\\resources\\algs4-data\\jobs.txt";
        String separator = "/";

        SymbolDigraph sg = new SymbolDigraph(path, separator);

        Topological top = new Topological(sg.G());

        // 跟书上输出的顺序不一致，大概是因为书上的测试数据和实际的jobs.txt先后顺序上有差异
        for (int v : top.order())
            System.out.println(sg.name(v));
    }
}
