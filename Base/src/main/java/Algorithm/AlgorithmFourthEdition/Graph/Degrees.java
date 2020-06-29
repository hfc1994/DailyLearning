package Algorithm.AlgorithmFourthEdition.Graph;

import Algorithm.AlgorithmFourthEdition.BagQueueAndStack.Queue;

/**
 * Created by user-hfc on 2020/6/21.
 *
 * 4.2.7 顶点的入度为指向该顶点的边的总数。顶点的出度为由该顶点指出的边的总数。从出度为0
 * 的顶点是不可能到达任何顶点的，这种顶点叫做终点；入度为0的顶点是不可能从任何顶点到达的，
 * 所以叫做起点。一副允许出现自环且每个顶点的出度均为1的有向图叫做映射（从0到V-1之间的整
 * 数到它们自身的函数）。
 */
public class Degrees {

    private int[] in;
    private int[] out;

    public Degrees(Digraph G) {
        in = new int[G.V()];
        out = new int[G.V()];
        for (int i=0; i<G.V(); i++) {
            out[i] = G.adj[i].size();
            for (int v : G.adj(i))
                in[v] += 1;
        }
    }

    /**
     * v的入度
     */
    public int indegree(int v) {
        return in[v];
    }

    /**
     * v的出度
     */
    public int outdegree(int v) {
        return out[v];
    }

    /**
     * 所有起点的集合
     */
    public Iterable<Integer> sources() {
        Queue<Integer> queue = new Queue<>();
        for (int i=0; i<in.length; i++) {
            if (in[i] == 0)
                queue.put(i);
        }
        return queue;
    }

    /**
     * 所有终点的集合
     */
    public Iterable<Integer> sinks() {
        Queue<Integer> queue = new Queue<>();
        for (int i=0; i<out.length; i++) {
            if (out[i] == 0)
                queue.put(i);
        }
        return queue;
    }

    /**
     * G是一副映射嘛
     */
    public boolean isMap() {
        boolean ret = true;
        for (int i=0; i<out.length; i++) {
            if (out[i] != 1)
                ret = false;
        }
        return ret;
    }
}
