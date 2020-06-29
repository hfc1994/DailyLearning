package Algorithm.AlgorithmFourthEdition.Graph;

/**
 * Created by user-hfc on 2020/6/23.
 *
 * 最小生成树的API
 */
public interface MST {

    /**
     * 最小生成树的所有边
     */
    Iterable<Edge> edges();

    /**
     * 最小生成树的权重
     */
    double weight();
}
