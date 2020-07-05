package Algorithm.AlgorithmFourthEdition.Graph;

import edu.princeton.cs.algs4.In;

import java.io.File;

/**
 * Created by user-hfc on 2020/7/5.
 *
 * 无环加权有向图的最短路径算法
 * 按照拓扑排序处理无环有向图中的顶点，不会再次遇到已经被放松过的顶点，
 * 因此该算法的效率几乎已经没有提高的空间了。
 */
public class AcyclicSP extends AbstractSP {

    public AcyclicSP(EdgeWeightedDigraph G, int s) {
        edgeTo = new DirectedEdge[G.V()];
        distTo = new double[G.V()];

        for (int v=0; v<G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;

        Topological topo = new Topological(G);
        for (int v : topo.order()) {
            relax(G, v);
        }
    }

    public static void main(String[] args) {
        String path = System.getProperty("user.dir") + "\\resources\\algs4-data\\tinyEWDAG.txt";
        File f = new File(path);

        EdgeWeightedDigraph G = new EdgeWeightedDigraph(new In(f));
        int s = 5;
        AcyclicSP sp = new AcyclicSP(G, s);

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
