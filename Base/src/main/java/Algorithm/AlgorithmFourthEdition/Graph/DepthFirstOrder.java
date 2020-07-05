package Algorithm.AlgorithmFourthEdition.Graph;

import Algorithm.AlgorithmFourthEdition.BagQueueAndStack.Queue;
import Algorithm.AlgorithmFourthEdition.BagQueueAndStack.Stack;

/**
 * Created by user-hfc on 2020/6/19.
 *
 * 有向图中基于深度优先搜索的顶点排序
 */
public class DepthFirstOrder {

    private boolean[] marked;
    private Queue<Integer> pre; // 所有顶点的前序排列
    private Queue<Integer> post; // 所有顶点的后序排列
    private Stack<Integer> reversePost; // 所有顶点的逆后序排列

    public DepthFirstOrder(Digraph G) {
        pre = new Queue<>();
        post = new Queue<>();
        reversePost = new Stack<>();
        marked = new boolean[G.V()];

        for (int v = 0; v < G.V(); v++) {
            if (!marked[v])
                dfs(G, v);
        }
    }

    public DepthFirstOrder(EdgeWeightedDigraph G) {
        pre = new Queue<>();
        post = new Queue<>();
        reversePost = new Stack<>();
        marked = new boolean[G.V()];

        for (int v = 0; v < G.V(); v++) {
            if (!marked[v])
                dfs(G, v);
        }
    }

    private void dfs(Digraph G, int v) {
        pre.put(v);

        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[w])
                dfs(G, w);
        }
        post.put(v);
        reversePost.push(v);
    }

    private void dfs(EdgeWeightedDigraph G, int v) {
        pre.put(v);

        marked[v] = true;
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();     // 微小的差异
            if (!marked[w])
                dfs(G, w);
        }
        post.put(v);
        reversePost.push(v);
    }

    public Iterable<Integer> pre() {
        return pre;
    }

    public Iterable<Integer> post() {
        return post;
    }

    public Iterable<Integer> reversePost() {
        return reversePost;
    }
}
