package Algorithm.AlgorithmFourthEdition.Graph;

import edu.princeton.cs.algs4.In;

/**
 * Created by user-hfc on 2020/6/18.
 *
 * 有向图的API
 */
public class Digraph extends Graph {

    public Digraph(int V) {
        super(V);
    }

    public Digraph(In in) {
        super(in);
    }

    @Override
    public void addEdge(int v, int w) {
        this.adj[v].add(w);
    }

    // 当前有向图的反转有向图
    public Digraph reverse() {
        Digraph R = new Digraph(this.V());
        for (int v = 0; v < this.V(); v++) {
            for (int w : adj(v))
                R.addEdge(w, v);
        }
        return R;
    }
}
