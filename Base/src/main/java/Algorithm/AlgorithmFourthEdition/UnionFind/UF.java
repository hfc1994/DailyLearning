package Algorithm.AlgorithmFourthEdition.UnionFind;

/**
 * Created by user-hfc on 2020/1/13.
 */
public interface UF {

    /**
     * 在p和q之间添加一条连接
     */
    void union(int p, int q);

    /**
     * p（0到N-1）所在的分量的标识符
     */
    int find(int p);

    /**
     * 如果p和q存在于同一个分量重则返回true
     */
    default boolean connection(int p, int q) {
        return find(p) == find(q);
    }

    /**
     * 连通分量的数量
     */
    int count();
}
