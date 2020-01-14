package Algorithm.AlgorithmFourthEdition.UnionFind;

/**
 * Created by user-hfc on 2020/1/14.
 *
 * 在WeightQuickUnion的基础上做路径压缩
 */
public class CompressWeightQuickUnion extends WeightQuickUnion {

    public CompressWeightQuickUnion(int N) {
        super(N);
    }

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
