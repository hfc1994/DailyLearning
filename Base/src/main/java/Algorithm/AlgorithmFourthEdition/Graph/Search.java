package Algorithm.AlgorithmFourthEdition.Graph;

/**
 * Created by user-hfc on 2020/6/14.
 *
 * 图处理算法的API
 */
public interface Search {

    /**
     * 点p是否与起始点连通
     * @param p 目标点
     */
    boolean marked(int p);

    /**
     * 与起始点连通的顶点总数
     */
    int count();
}
