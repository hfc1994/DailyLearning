package Algorithm.AlgorithmFourthEdition.Graph;

import edu.princeton.cs.algs4.In;

import java.io.File;

/**
 * Created by user-hfc on 2020/6/14.
 *
 * 深度优先遍历搜索
 */
public class DepthFirstSearch implements Search {

    private boolean[] marked;   // 是否已被访问
    private int count;  // 与起始点连通的顶点总数

    // s是起始点start
    public DepthFirstSearch(Graph G, int s) {
        marked = new boolean[G.V()];
        dfs(G, s);
    }

    private void dfs(Graph G, int s) {
        marked[s] = true;   // 已被访问
        count++;
        for (int p : G.adj(s))
            if (!marked[p]) dfs(G, p);
    }

    @Override
    public boolean marked(int p) {
        return marked[p];
    }

    @Override
    public int count() {
        return count;
    }

    public static void main(String[] args) {
        String path = System.getProperty("user.dir") + "\\resources\\algs4-data\\tinyG.txt";
        File f = new File(path);

        Graph G = new Graph(new In(f));
        int s = 0;
        Search search = new DepthFirstSearch(G, s);

        for (int p=0; p<G.V(); p++) {
            if (search.marked(p))
                System.out.print(p + " ");
        }
        System.out.println();

        // 判断是否是连通图
        if (search.count() != G.V())
            System.out.print("NOT ");
        System.out.println("connected");
    }
}
