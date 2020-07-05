package Algorithm.AlgorithmFourthEdition.Graph;

import Algorithm.AlgorithmFourthEdition.BagQueueAndStack.Stack;

/**
 * Created by user-hfc on 2020/7/5.
 *
 * 最短路径算法的抽象实现
 */
abstract class AbstractSP implements SP {

    protected DirectedEdge[] edgeTo;    // 从起点到某个顶点的最后一条边
    protected double[] distTo;      // 从起点到某个顶点的路径长度

    @Override
    public double distTo(int v) {
        return distTo[v];
    }

    @Override
    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    @Override
    public Iterable<DirectedEdge> pathTo(int v) {
        if (!hasPathTo(v))
            return null;

        Stack<DirectedEdge> path = new Stack<>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()])
            path.push(e);
        return path;
    }

    @Override
    public void relax(DirectedEdge e) {
        int v = e.from();
        int w = e.to();
        if (distTo[w] > distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
        }
    }

    @Override
    public void relax(EdgeWeightedDigraph G, int v) {
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();
            if (distTo[w] > distTo[v] + e.weight()) {
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
            }
        }
    }
}
