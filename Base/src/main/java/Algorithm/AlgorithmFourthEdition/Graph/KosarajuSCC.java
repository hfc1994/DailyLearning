package Algorithm.AlgorithmFourthEdition.Graph;

import edu.princeton.cs.algs4.In;

/**
 * Created by user-hfc on 2020/6/20.
 *
 * 计算强连通分量的Kosaraju算法
 * 使用深度优先搜索查找给定有向图G的方向图Gr，根据由此得到的所有顶点
 * 的逆后序再次用深度优先搜索处理有向图G（Kosaraju算法），其构造函数中
 * 的每一次递归调用所标记的顶点都在同一个强连通分量之中。
 */
public class KosarajuSCC implements SCC {

    private boolean[] marked;   // 已访问过的顶点
    private int[] id;   // 强连通分量的标识符
    private int count;  // 强连通分量的数量

    public KosarajuSCC(Digraph G) {
        marked = new boolean[G.V()];
        id = new int[G.V()];
        DepthFirstOrder order = new DepthFirstOrder(G.reverse());
        for (int s : order.reversePost()) {
            if (!marked[s]) {
                dfs(G, s);
                count++;
            }
        }
    }

    private void dfs(Digraph G, int v) {
        marked[v] = true;
        id[v] = count;
        for (int w : G.adj[v]) {
            if (!marked[w])
                dfs(G, w);
        }
    }

    @Override
    public boolean stronglyConnected(int v, int w) {
        return id[v] == id[w];
    }

    @Override
    public int count() {
        return count;
    }

    @Override
    public int id(int v) {
        return id[v];
    }

    public static void main(String[] args) {
        String path = System.getProperty("user.dir") + "\\resources\\algs4-data\\tinyDG.txt";

        Digraph G = new Digraph(new In(path));
        KosarajuSCC scc = new KosarajuSCC(G);

        System.out.println(scc.count + " components");
        for (int i=0; i<scc.count; i++) {
            for (int j=0; j<scc.id.length; j++) {
                if (i == scc.id[j])
                    System.out.print(j + " ");
            }
            System.out.println();
        }
    }
}
