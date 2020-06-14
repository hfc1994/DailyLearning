package Algorithm.AlgorithmFourthEdition.Graph;

import Algorithm.AlgorithmFourthEdition.BagQueueAndStack.Bag;
import edu.princeton.cs.algs4.In;

/**
 * Created by user-hfc on 2020/6/14.
 *
 * 无向图
 */
public class Graph {

    private final int V;    // 顶点数目
    private int E;  // edge边的数组
    private Bag<Integer>[] adj; // 邻接表

    @SuppressWarnings("unchecked")
    public Graph(int V) {
        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V];  // 创建邻接表
        for (int i=0; i<V; i++)     // 将所有链表初始化为空
            adj[i] = new Bag<>();
    }

    public Graph(In in) {
        this(in.readInt());     // 读取V并将图初始化
        int E = in.readInt();   // 读取E
        for (int i=0; i<E; i++) {
            int v = in.readInt();   // 读取一个顶点
            int w = in.readInt();   // 读取另一个顶点
            addEdge(v, w);  // 添加一条连接它们的边
        }
    }

    // 定点数目
    public int V() {
        return V;
    }

    // 边数目
    public int E() {
        return E;
    }

    // 添加边
    public void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v);
        E++;
    }

    // 点v的所有相邻点
    public Iterable<Integer> adj(int v) {
        return adj[v];
    }
}
