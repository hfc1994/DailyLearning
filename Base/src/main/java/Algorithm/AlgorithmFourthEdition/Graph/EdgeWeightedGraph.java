package Algorithm.AlgorithmFourthEdition.Graph;

import Algorithm.AlgorithmFourthEdition.BagQueueAndStack.Bag;
import edu.princeton.cs.algs4.In;

/**
 * Created by user-hfc on 2020/6/23.
 *
 * 加权无向图
 */
public class EdgeWeightedGraph {

    private final int V;    // 顶点的总数
    private int E;          // 边的总数
    private Bag<Edge>[] adj;    // 邻接表

    @SuppressWarnings("unchecked")
    public EdgeWeightedGraph(int V) {
        this.V = V;
        this.E = 0;
        adj = (Bag<Edge>[]) new Bag[V];
        for (int i=0; i<V; i++) {
            adj[i] = new Bag<>();
        }
    }

    @SuppressWarnings("unchecked")
    public EdgeWeightedGraph(In in) {
        this.V = in.readInt();
        int E = in.readInt();
        adj = (Bag<Edge>[]) new Bag[V];
        for (int i=0; i<V; i++) {
            adj[i] = new Bag<>();
        }

        for (int i=0; i<E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            double weight = in.readDouble();
            this.addEdge(new Edge(v, w, weight));
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public void addEdge(Edge e) {
        int v = e.either();
        int w = e.other(v);
        adj[v].add(e);
        adj[w].add(e);
        E++;
    }

    public Iterable<Edge> adj(int v) {
        return adj[v];
    }

    public Iterable<Edge> edges() {
        Bag<Edge> b = new Bag<>();
        for (int i=0; i<V; i++) {
            for (Edge e : adj[i]) {
                if (e.other(i) > i)
                    b.add(e);
            }
        }
        return b;
    }

//    public static void main(String[] args) {
//        String path = System.getProperty("user.dir") + "\\resources\\algs4-data\\tinyEWG.txt";
//        In in = new In(path);
//        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
//
//
//    }
}
