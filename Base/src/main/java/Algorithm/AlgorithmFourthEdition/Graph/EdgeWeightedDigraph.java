package Algorithm.AlgorithmFourthEdition.Graph;

import Algorithm.AlgorithmFourthEdition.BagQueueAndStack.Bag;
import edu.princeton.cs.algs4.In;

/**
 * Created by user-hfc on 2020/7/5.
 *
 * 加权有向图的数据结构
 */
public class EdgeWeightedDigraph {

    private final int V;    // 顶点的总数
    private int E;          // 边的总数
    private Bag<DirectedEdge>[] adj;    // 邻接表

    @SuppressWarnings("unchecked")
    public EdgeWeightedDigraph(int V) {
        this.V = V;
        this.E = 0;
        adj = (Bag<DirectedEdge>[]) new Bag[V];
        for (int v=0; v<V; v++)
            adj[v] = new Bag<>();
    }

    public EdgeWeightedDigraph(In in) {
        this(in.readInt());
        int E = in.readInt();
        for (int i=0; i<E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            double weight = in.readDouble();
            addEdge(new DirectedEdge(v, w, weight));
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public void addEdge(DirectedEdge e) {
        adj[e.from()].add(e);
        E++;
    }

    public Iterable<DirectedEdge> adj(int v) {
        return adj[v];
    }

    public Iterable<DirectedEdge> edges() {
        Bag<DirectedEdge> bag = new Bag<>();
        for (int v=0; v<V; v++) {
            for (DirectedEdge e : adj[v]) {
                bag.add(e);
            }
        }
        return bag;
    }
}
