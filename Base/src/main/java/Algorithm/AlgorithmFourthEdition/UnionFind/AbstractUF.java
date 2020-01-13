package Algorithm.AlgorithmFourthEdition.UnionFind;

/**
 * Created by user-hfc on 2020/1/13.
 */
abstract class AbstractUF implements UF {

    protected int[] id; // 分量id（以触点作为索引）
    protected int count;    // 分量数量

    @Override
    public abstract void union(int p, int q);

    @Override
    public abstract int find(int p);

    @Override
    public int count() {
        return count;
    }
}
