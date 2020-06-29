package Algorithm.AlgorithmFourthEdition.Graph;

import Algorithm.AlgorithmFourthEdition.BagQueueAndStack.Queue;
import Algorithm.AlgorithmFourthEdition.PriorityQueue.MinPQ;
import Algorithm.AlgorithmFourthEdition.UnionFind.QuickFind;
import Algorithm.AlgorithmFourthEdition.UnionFind.UF;

/**
 * Created by user-hfc on 2020/6/29.
 *
 * 最小生成树的Kruskal算法的即时实现
 *
 * 主要思想：把所有边都添加到优先队列（从小到大）中，然后依次选取最短的边添加到生成树中，
 * 当边被取完，或者是生成树的边数等于定点数-1时，此时构建完成最小生成树。所以该种方式是
 * 从星星点点到连接成片最后到森林。
 *
 */
public class KruskalMST implements MST {

    private Queue<Edge> mst;
    private double weight;      // 所有边的权重值之和

    public KruskalMST(EdgeWeightedGraph G) {
        mst = new Queue<>();
        MinPQ<Edge> pq = new MinPQ<>();
        for (Edge e : G.edges())
            pq.insert(e);
        UF uf = new QuickFind(G.V());   // 连通图
        weight = 0.0;

        while (!pq.isEmpty() && mst.size() < G.V() - 1) {
            Edge e = pq.delMin();   // 从pq得到权重最小的边和它的顶点
            int v = e.either();
            int w = e.other(v);
            if (uf.connected(v, w))     // 两个顶点相连，说明该边已经在生成树中
                continue;   // 忽略失效的边
            uf.union(v, w); // 合并分量
            mst.put(e);     // 将边添加到最小生成树中
            weight += e.weight();
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
