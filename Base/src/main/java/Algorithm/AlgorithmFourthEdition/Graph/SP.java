package Algorithm.AlgorithmFourthEdition.Graph;

/**
 * Created by user-hfc on 2020/7/5.
 *
 * 最短路径的接口
 */
public interface SP {

    /**
     * 从起点s到v的距离，如果不存在则路径为无穷大
     */
    double distTo(int v);

    /**
     * 是否存在从起点s到v的路径
     */
    boolean hasPathTo(int v);

    /**
     * 从起点s到v的路径，如果不存在则为null
     */
    Iterable<DirectedEdge> pathTo(int v);

    /**
     * 放松指定的边
     */
    void relax(DirectedEdge e);

    /**
     * 放松指定的点
     */
    void relax(EdgeWeightedDigraph G, int v);
}
