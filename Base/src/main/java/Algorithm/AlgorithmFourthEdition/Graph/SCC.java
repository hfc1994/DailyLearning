package Algorithm.AlgorithmFourthEdition.Graph;

/**
 * Created by user-hfc on 2020/6/20.
 *
 * 强连通分量的API
 */
public interface SCC {

    /**
     * v和w是否是强连通的
     */
    boolean stronglyConnected(int v, int w);

    /**
     * 图中的强连通分量的总数
     */
    int count();

    /**
     * v所在的强连通分量的标识符（在0至count()-1之间）
     */
    int id(int v);
}
