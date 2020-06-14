package Algorithm.AlgorithmFourthEdition.Graph;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;

import java.io.File;

/**
 * Created by user-hfc on 2020/6/14.
 *
 * 使用深度优先搜索的连通分量计算
 */
public class DfsCC implements CC {

    private boolean[] marked;
    private int[] id;   // 连通分量标识符
    private int count;  // 连通分量

    public DfsCC(Graph G) {
        marked = new boolean[G.V()];
        id = new int[G.V()];

        for (int p=0; p<G.V(); p++) {
            if (!marked[p]) {
                dfs(G, p);
                count++;
            }
        }
    }

    public void dfs(Graph G, int s) {
        marked[s] = true;
        id[s] = count;
        for (int p : G.adj(s)) {
            if (!marked[p])
                dfs(G, p);
        }
    }

    @Override
    public boolean connected(int v, int w) {
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
        String path = System.getProperty("user.dir") + "\\resources\\algs4-data\\tinyG.txt";
        File f = new File(path);

        Graph G = new Graph(new In(f));
        CC cc = new DfsCC(G);

        int M = cc.count();
        System.out.println(M + " components");

        Bag<Integer>[] components = (Bag<Integer>[]) new Bag[M];
        for (int i=0; i<M; i++)
            components[i] = new Bag<>();
        for (int i=0; i<G.V(); i++)
            components[cc.id(i)].add(i);
        for (int i=0; i<M; i++) {
            for (int v : components[i])
                System.out.print(v + " ");
            System.out.println();
        }

    }
}
