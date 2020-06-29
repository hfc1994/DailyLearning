package Algorithm.AlgorithmFourthEdition.Graph;

import Algorithm.AlgorithmFourthEdition.BagQueueAndStack.Queue;
import Algorithm.AlgorithmFourthEdition.PriorityQueue.MinPQ;

/**
 * Created by user-hfc on 2020/6/23.
 *
 * 最小生成树的Prim算法的延时实现
 *
 * 主要思想：从起始顶点开始，不断为最小生成树找寻它所能获取到的最小权值的边
 * 该种方式的最小生成树是由点到线再到面，覆盖面逐渐扩大的
 *
 * 延迟体现在，只有当取出最小权值边的时候才判断当前边是否已经失效（两端点都在最小生成树上）
 */
public class LazyPrimMST implements MST {

    private boolean[] marked;   // 最小生成树的顶点
    private Queue<Edge> mst;    // 最小生成树的边
    private MinPQ<Edge> pq;     // 横切边（包括失败的边）
    private double weight;      // 所有边的权重值之和

    public LazyPrimMST(EdgeWeightedGraph G) {
        pq = new MinPQ<>();
        marked = new boolean[G.V()];
        mst = new Queue<>();
        weight = 0.0;

        visit(G, 0);    // 假设G是连通的
        while (!pq.isEmpty()) {
            Edge e = pq.delMin();   // 从pq中得到权重最小的边

            int v = e.either();
            int w = e.other(v);
            if (marked[v] && marked[w])
                continue;   // 跳过失效的边
            mst.put(e);
            weight += e.weight();

            // 将顶点(v或w)添加到树中
            if (!marked[v]) visit(G, v);
            if (!marked[w]) visit(G, w);
        }
    }

    private void visit(EdgeWeightedGraph G, int v) {
        // 标记顶点v，并将所有连接v和未被标记的边加入pq
        marked[v] = true;
        for (Edge e : G.adj(v)) {
            if (!marked[e.other(v)])
                pq.insert(e);
        }
    }

    @Override
    public Iterable<Edge> edges() {
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
