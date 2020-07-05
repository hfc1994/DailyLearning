package Algorithm.AlgorithmFourthEdition.Graph;

import Algorithm.AlgorithmFourthEdition.BagQueueAndStack.Stack;

/**
 * Created by user-hfc on 2020/6/18.
 *
 * 有向环的API
 */
public class DirectedCycle {

    private boolean[] marked;
    private int[] edgeTo;
    private Stack<Integer> cycle;   // 有向环中的所有顶点（如果存在环上）
    private boolean[] onStack;  // 递归调用的栈上的所有顶点

    public DirectedCycle(Digraph G) {
        onStack = new boolean[G.V()];
        edgeTo = new int[G.V()];
        marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++) {
            if (!marked[v])
                dfs(G, v);
        }
    }

    public DirectedCycle(EdgeWeightedDigraph G) {
        onStack = new boolean[G.V()];
        edgeTo = new int[G.V()];
        marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++) {
            if (!marked[v])
                dfs(G, v);
        }
    }

    private void dfs(Digraph G, int v) {
        onStack[v] = true;
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (this.hasCycle())
                return;
            else if (!marked[w]) {
                edgeTo[w] = v;
                dfs(G, w);
            } else if (onStack[w]) {
                // 进入此处的条件是marked[w] == true && onStack[w] == true
                // 只有在最后一次的dfs()里才会进入此语句块
                cycle = new Stack<>();
                // 环的首和尾分别是v和w
                for (int x = v; x != w; x = edgeTo[x])
                    cycle.push(x);

                cycle.push(w);
                cycle.push(v);
            }
        }
        onStack[v] = false;
    }

    private void dfs(EdgeWeightedDigraph G, int v) {
        onStack[v] = true;
        marked[v] = true;
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();     // 微小的差异
            if (this.hasCycle())
                return;
            else if (!marked[w]) {
                edgeTo[w] = v;
                dfs(G, w);
            } else if (onStack[w]) {
                // 进入此处的条件是marked[w] == true && onStack[w] == true
                // 只有在最后一次的dfs()里才会进入此语句块
                cycle = new Stack<>();
                // 环的首和尾分别是v和w
                for (int x = v; x != w; x = edgeTo[x])
                    cycle.push(x);

                cycle.push(w);
                cycle.push(v);
            }
        }
        onStack[v] = false;
    }

    public boolean hasCycle() {
        return cycle != null;
    }

    public Iterable<Integer> cycle() {
        return cycle;
    }
}
