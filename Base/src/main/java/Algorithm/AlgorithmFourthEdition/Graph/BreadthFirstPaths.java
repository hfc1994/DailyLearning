package Algorithm.AlgorithmFourthEdition.Graph;

import Algorithm.AlgorithmFourthEdition.BagQueueAndStack.Queue;
import Algorithm.AlgorithmFourthEdition.BagQueueAndStack.Stack;
import edu.princeton.cs.algs4.In;

import java.io.File;

/**
 * Created by user-hfc on 2020/6/14.
 *
 * 使用广度优先搜索查找图中的路径
 */
public class BreadthFirstPaths implements Paths {

    private boolean[] marked;   // 该点是否被访问过了
    private int[] edgeTo;   // 从顶点到当前点路径的上一个顶点
    private final int s;    // 起点s

    public BreadthFirstPaths(Graph G, int s) {
        this.marked = new boolean[G.V()];
        this.edgeTo = new int[G.V()];
        this.s = s;
        bfs(G, s);
    }

    public void bfs(Graph G, int s) {
        Queue<Integer> queue = new Queue<>();
        marked[s] = true;   // 标记起点
        queue.put(s);       // 将起点加入队列

        while (!queue.isEmpty()) {
            int v = queue.take();   // 从队列中取出下一个顶点
            for (int p : G.adj(v)) {
                if (!marked[p]) {
                    marked[p] = true;   // 标记为已访问
                    edgeTo[p] = v;      // 保存最短路径的最后一条边
                    queue.put(p);       // 把当前点加入到队列
                }
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
        Paths search = new BreadthFirstPaths(G, s);

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
