package Algorithm.AlgorithmFourthEdition.Graph;

/**
 * Created by user-hfc on 2020/7/5.
 *
 * 无环加权有向图的最长路径算法
 */
public class AcyclicLP extends AbstractSP {

    public AcyclicLP(EdgeWeightedDigraph G, int s) {
        edgeTo = new DirectedEdge[G.V()];
        distTo = new double[G.V()];

        for (int v=0; v<G.V(); v++)
            distTo[v] = Double.NEGATIVE_INFINITY;
        distTo[s] = 0.0;

        Topological topo = new Topological(G);

        if (topo.order() == null)
            return;
        for (int v : topo.order()) {
            relax(G, v);
        }
    }

    @Override
    public void relax(DirectedEdge e) {
        int v = e.from();
        int w = e.to();
        if (distTo[w] < distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
        }
    }

    @Override
    public void relax(EdgeWeightedDigraph G, int v) {
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();
            if (distTo[w] < distTo[v] + e.weight()) {
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
            }
        }
    }
}
