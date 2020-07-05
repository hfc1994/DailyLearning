package Algorithm.AlgorithmFourthEdition.Graph;

/**
 * Created by user-hfc on 2020/7/5.
 *
 * 加权有向边
 */
public class DirectedEdge {

    private final int v;  // 边的起始点
    private final int w;  // 边的结束点
    private final double weight;   // 边的权重

    public DirectedEdge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public double weight() {
        return weight;
    }

    public int from() {
        return v;
    }

    public int to() {
        return w;
    }

    public String toString() {
        return String.format("%d->%d %.2f", v, w, weight);
    }
}
