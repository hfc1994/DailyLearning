package Algorithm.AlgorithmFourthEdition.Graph;

/**
 * Created by user-hfc on 2020/6/14.
 *
 * 用符号作为顶点的图的API
 */
public interface Symbol {

    /**
     * key是一个顶点嘛
     */
    boolean contains(String key);

    /**
     * key的索引
     */
    int index(String key);

    /**
     * 索引v的顶点名
     */
    String name(int v);

    /**
     * 隐藏的Graph对象
     */
    Graph G();
}
