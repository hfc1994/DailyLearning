package Algorithm.AlgorithmFourthEdition.Graph;

import Algorithm.AlgorithmFourthEdition.BagQueueAndStack.Bag;
import Algorithm.AlgorithmFourthEdition.PriorityQueue.IndexMinPQ;

/**
 * Created by user-hfc on 2020/6/23.
 *
 * 最小生成树的Prim算法的即时实现
 *
 * 主要思想：从起始顶点开始，不断为最小生成树找寻它所能获取到的最小权值的边
 * 该种方式的最小生成树是由点到线再到面，覆盖面逐渐扩大的
 *
 * 即时体现在，当出现一条到达w点的边e时会被立马加入到IndexMinPQ中，只有后续
 * 出现更小权值的到达w的边e2时才会替换e。
 */
public class PrimMST implements MST {

    private Edge[] edgeTo;      // 距离树最近的边
    private double[] distTo;    // distTo[w]=edgeTo[w].weight()
    private boolean[] marked;   // 如果v在树中则为true
    private IndexMinPQ<Double> pq;  // 有效的横切边
    private double weight;      // 所有边的权重值之和

    public PrimMST(EdgeWeightedGraph G) {
        edgeTo = new Edge[G.V()];
        distTo = new double[G.V()];
        marked = new boolean[G.V()];
        for (int v=0; v<G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        pq = new IndexMinPQ<>(G.V());
        weight = 0.0;

        distTo[0] = 0.0;
        pq.insert(0, 0.0);  // 用顶点0和权重0初始化pq
        while (!pq.isEmpty())
            visit(G, pq.delMinGetIndex());  // 将最近的顶点添加到树中
    }

    private void visit(EdgeWeightedGraph G, int v) {
        // 将顶点v添加到树中，更新数据
        marked[v] = true;
        weight += distTo[v];
        for (Edge e : G.adj(v)) {
            int w = e.other(v);

            if (marked[w])
                continue;   // v-w失败
            if (e.weight() < distTo[w]) {
                // 连接w和树的最佳边Edge变为e
                edgeTo[w] = e;
                distTo[w] = e.weight();
                if (pq.contains(w))
                    pq.change(w, distTo[w]);
                else
                    pq.insert(w, distTo[w]);
            }
        }
    }

    /**
     * 4.3.21 为PrimMST类实现edges()方法
     */
    @Override
    public Iterable<Edge> edges() {
        Bag<Edge> mst = new Bag<>();
        for (int v=1; v<edgeTo.length; v++)
            mst.add(edgeTo[v]);
        return mst;
    }

    /**
     * 4.3.31 最小生成树的权重。为LazyPrimMST、PrimMST和KruskalMST实现weight()方法。
     */
    @Override
    public double weight() {
        return weight;
    }
}
