package Algorithm.AlgorithmFourthEdition.Graph;

import Algorithm.AlgorithmFourthEdition.BagQueueAndStack.Queue;

/**
 * Created by user-hfc on 2020/7/5.
 *
 * 基于队列的Bellman-Ford算法
 * 在从s无法到达任何负权重环的情况下，以任意顺序放松有向图的所有边，重复V轮
 */
public class BellmanFordSP extends AbstractSP {

    private boolean[] onQ;  // 该顶点是否存在于队列中
    private Queue<Integer> queue;   // 正在被放松的顶点
    private int cost;   // relax()的调用次数
    private Iterable<Integer> cycle;   // edgeTo[]中是否有负权重环

    public BellmanFordSP(EdgeWeightedDigraph G, int s) {
        distTo = new double[G.V()];
        edgeTo = new DirectedEdge[G.V()];
        onQ = new boolean[G.V()];
        queue = new Queue<>();

        for (int v=0; v<G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;
        queue.put(s);
        onQ[s] = true;
        while (!queue.isEmpty() && !hasNegativeCycle()) {
            int v = queue.take();
            onQ[v] = false; // 需要重复V轮
            relax(G, v);
        }
    }

    @Override
    public void relax(EdgeWeightedDigraph G, int v) {
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();
            if (distTo[w] > distTo[v] + e.weight()) {
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
                if (!onQ[w]) {
                    queue.put(w);
                    onQ[w] = true;
                }
            }

            if (cost++ % G.V() == 0)
                findNegativeCycle();
        }
    }

    private void findNegativeCycle() {
        int V = edgeTo.length;
        EdgeWeightedDigraph spt = new EdgeWeightedDigraph(V);
        for (int v=0; v<V; v++) {
            if (edgeTo[v] != null)
                spt.addEdge(edgeTo[v]);
        }

        // FIXME: 2020/7/5 这个只能检测是否有环，不能检测是否是负权重环
        DirectedCycle cycleFinder = new DirectedCycle(spt);
        cycle = cycleFinder.cycle();
    }

    public boolean hasNegativeCycle() {
        return cycle != null;
    }

    public Iterable<Integer> negativeCycle() {
        return cycle;
    }
}
