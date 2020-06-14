package Algorithm.AlgorithmFourthEdition.Graph;

import Algorithm.AlgorithmFourthEdition.BagQueueAndStack.Stack;
import edu.princeton.cs.algs4.In;

import java.io.File;

/**
 * Created by user-hfc on 2020/6/14.
 *
 * 深度优先搜索查找图中的路径
 */
public class DepthFirstPaths implements Paths {

    private boolean[] marked;   // 对应顶点上是否调用过dfs()
    private int[] edgeTo;   // 从起点到一个定点的已知路径上的最后一个顶点
    private final int s;    // 起点

    public DepthFirstPaths(Graph G, int s) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        this.s = s;
        dfs(G, s);
    }

    private void dfs(Graph G, int s) {
        marked[s] = true;
        for (int p : G.adj(s)) {
            if (!marked[p]) {
                edgeTo[p] = s;  // 表明有一条s-p的边，这么存储可以方便的p->s
                dfs(G, p);
            }
        }
    }

    @Override
    public boolean hashPathTo(int p) {
        return marked[p];
    }

    @Override
    public Iterable<Integer> pathTo(int p) {
        if (!hashPathTo(p))
            return null;
        // 后续获取到的数据顺序是反的，正好用上Stack后进先出的特性
        Stack<Integer> stack = new Stack<>();
        int index = p;
        while (index != s) {
            stack.push(index);
            index = edgeTo[index];
        }
        stack.push(s);
        return stack;
    }

    public static void main(String[] args) {
        String path = System.getProperty("user.dir") + "\\resources\\algs4-data\\tinyCG.txt";
        File f = new File(path);

        Graph G = new Graph(new In(f));
        int s = 0;
        Paths search = new DepthFirstPaths(G, s);

        for (int p=0; p<G.V(); p++) {
            System.out.print(s + " to " + p + ": ");
            if (search.hashPathTo(p)) {
                for (int x : search.pathTo(p)) {
                    if (x == s)
                        System.out.print(x);
                    else
                        System.out.print("-" + x);
                }
            }
            System.out.println();
        }
    }
}
