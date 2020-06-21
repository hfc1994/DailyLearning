package Algorithm.AlgorithmFourthEdition.Graph;

import Algorithm.AlgorithmFourthEdition.BagQueueAndStack.Bag;
import edu.princeton.cs.algs4.In;

import java.io.File;

/**
 * Created by user-hfc on 2020/6/18.
 *
 * 有向图的可达性
 */
public class DirectedDFS {

    private boolean[] marked;

    /**
     * 在G中找到从s可达的所有顶点
     */
    public DirectedDFS(Digraph G, int s) {
        marked = new boolean[G.V()];
        dfs(G, s);
    }

    /**
     * 在G中找到从sources中的所有顶点可达的所有顶点
     */
    public DirectedDFS(Digraph G, Iterable<Integer> sources) {
        marked = new boolean[G.V()];
        for (int s : sources) {
            if (!marked[s])
                dfs(G, s);
        }
    }

    private void dfs(Digraph G, int v) {
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[w])
                dfs(G, w);
        }
    }

    /**
     * v是否可达的
     */
    public boolean marked(int v) {
        return marked[v];
    }

    public static void main(String[] args) {
        String path = System.getProperty("user.dir") + "\\resources\\algs4-data\\tinyDG.txt";
        File f = new File(path);
        int[] starts = new int[] { 1, 2 , 6 };

        Digraph G = new Digraph(new In(f));

        Bag<Integer> sources = new Bag<>();
        for (int i = 1; i < starts.length; i++)
            sources.add(starts[i]);

        DirectedDFS reachable = new DirectedDFS(G, sources);

        for (int v = 0; v < G.V(); v++) {
            if (reachable.marked[v])
                System.out.print(v + " ");
        }
        System.out.println();
    }
}
