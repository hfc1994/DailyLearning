package Algorithm.AlgorithmFourthEdition.Graph;

import Algorithm.AlgorithmFourthEdition.BagQueueAndStack.Stack;
import Algorithm.AlgorithmFourthEdition.PriorityQueue.IndexMinPQ;
import edu.princeton.cs.algs4.In;

import java.io.File;

/**
 * Created by user-hfc on 2020/7/5.
 *
 * 最短路径的Dijkstra算法
 * 该算法的结果是，起点s到其它所有顶点的所有最短路径
 */
public class DijkstraSP extends AbstractSP {

    private IndexMinPQ<Double> pq;

    public DijkstraSP(EdgeWeightedDigraph G, int s) {
        edgeTo = new DirectedEdge[G.V()];
        distTo = new double[G.V()];
        pq = new IndexMinPQ<>(G.V());

        for (int v=0; v<G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;

        distTo[s] = 0.0;
        pq.insert(s, 0.0);
        while (!pq.isEmpty())
            relax(G, pq.delMinGetIndex());
    }

    @Override
    public void relax(DirectedEdge e) {
        int v = e.from();
        int w = e.to();
        if (distTo[w] > distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;

            if (pq.contains(w))
                pq.change(w, distTo[w]);
            else
                pq.insert(w, distTo[w]);
        }
    }


    @Override
    public void relax(EdgeWeightedDigraph G, int v) {
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();
            if (distTo[w] > distTo[v] + e.weight()) {
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;

                if (pq.contains(w))
                    pq.change(w, distTo[w]);
                else
                    pq.insert(w, distTo[w]);
            }
        }
    }

    public static void main(String[] args) {
        String path = System.getProperty("user.dir") + "\\resources\\algs4-data\\tinyEWD.txt";
        File f = new File(path);

        EdgeWeightedDigraph G = new EdgeWeightedDigraph(new In(f));
        int s = 0;
        DijkstraSP sp = new DijkstraSP(G, s);

        for (int t=0; t<G.V(); t++) {
            System.out.print(s + " to " + t);
            System.out.printf(" (%4.2f): ", sp.distTo(t));
            if (sp.hasPathTo(t)) {
                for (DirectedEdge e : sp.pathTo(t))
                    System.out.print(e + "   ");
            }
            System.out.println();
        }
    }
}
