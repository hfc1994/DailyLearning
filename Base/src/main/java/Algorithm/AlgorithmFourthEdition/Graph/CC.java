package Algorithm.AlgorithmFourthEdition.Graph;

/**
 * Created by user-hfc on 2020/6/14.
 *
 * 连通分量的API
 */
public interface CC {

    /**
     * v和w是否连通
     */
    boolean connected(int v, int w);

    /**
     * 连通分量数
     */
    int count();

    /**
     * v所在的连通分量的标识符（0 ~ count()-1）
     */
    int id(int v);
}
