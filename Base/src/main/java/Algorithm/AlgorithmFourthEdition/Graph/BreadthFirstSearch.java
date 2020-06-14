package Algorithm.AlgorithmFourthEdition.Graph;

import Algorithm.AlgorithmFourthEdition.BagQueueAndStack.Queue;
import edu.princeton.cs.algs4.In;

import java.io.File;

/**
 * Created by user-hfc on 2020/6/14.
 *
 * 广度优先遍历搜索
 */
public class BreadthFirstSearch implements Search {

    private boolean[] marked;
    private int count;

    // s为起始点start
    public BreadthFirstSearch(Graph G, int s) {
        marked = new boolean[G.V()];
        bfs(G, s);
    }

    public void bfs(Graph G, int s) {
        Queue<Integer> queue = new Queue<>();
        marked[s] = true;
        queue.put(s);

        while (!queue.isEmpty()) {
            int p = queue.take();
            for (int x : G.adj(p)) {
                if (!marked[x]) {
                    marked[x] = true;
                    count++;
                    queue.put(x);   // 队列先进先出
                }
            }
        }
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
        Search search = new BreadthFirstSearch(G, s);

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
