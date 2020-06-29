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

    /**
     * 4.2.3 为Digraph添加一个构造函数，它接受一副有向图G然后创建
     * 并初始化这幅图的一个副本。G的用例的对它作出的任何改动都不应
     * 该影响到它的副本。
     */
    public Digraph(Digraph base) {
        super(base.V());
        for (int v=0; v<base.V(); v++) {
            for (int w : base.adj(v)) {
                addEdge(v, w);
            }
        }
    }

    @Override
    public void addEdge(int v, int w) {

        /*
         * 4.2.5 修改Digraph，不允许存在平行边和自环
         */
        if (hasEdge(w, v) || v == w)
            return;

        this.adj[v].add(w);
        this.E++;
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

    /**
     * 4.2.4 为Digraph添加一个方法hasEdge()，它接受两个整型参数v
     * 和w。如果图含有边v→w，方法返回true，否则返回false。
     */
    public boolean hasEdge(int v, int w) {
        for (int i : this.adj(v)) {
            if (i == w)
                return true;
        }
        return false;
    }
}
