package Algorithm.AlgorithmFourthEdition.UnionFind;

/**
 * Created by user-hfc on 2020/1/14.
 *
 * 在WeightQuickUnion的基础上做路径压缩
 *
 * 1.5.12 使用路径压缩的quick-union算法。根据路径压缩修改quick-union算法（详见1.5.2.3节），
 * 在find()方法中添加一个循环来将从p到根节点的路径上的每个触点都连接到根节点。
 */
public class CompressWeightQuickUnion extends WeightQuickUnion {

    public CompressWeightQuickUnion(int N) {
        super(N);
    }

    // TODO: 2020/1/16 优化，没达到想象中的效果
    // 把路径上的元素都指向根元素，达到路径压缩的效果
    @Override
    public int find(int p) {
        int begin = p;
        while (id[p] != p)
            p = id[p];

        int index = begin;
        while (p != index) {
            begin = id[index];
            id[index] = p;
            index = begin;
        }
        return p;
    }

}
