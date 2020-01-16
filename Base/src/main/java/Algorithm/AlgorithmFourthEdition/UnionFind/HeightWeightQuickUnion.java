package Algorithm.AlgorithmFourthEdition.UnionFind;

/**
 * Created by user-hfc on 2020/1/16.
 *
 * 1.5.14 根据高度加权的quick-union算法。给出UF的一个实现，使用和
 * 加权quick-union算法相同的策略但记录的是树的高度并总是将较矮的树
 * 连接到较高的树上。
 */
public class HeightWeightQuickUnion extends AbstractUF {

    private int[] height;   // 每个触点的高度，最大高度值在根触点

    public HeightWeightQuickUnion(int N) {
        id = new int[N];
        height = new int[N];
        count = N;
        for (int i=0; i<N; i++) {
            id[i] = i;
            height[i] = 0;
        }
    }

    @Override
    public void union(int p, int q) {
        int pId = find(p);
        int qId = find(q);

        if (pId == qId)
            return;

        int pH = height[pId], qH = height[qId];
        if (pH >= qH) {
            id[qId] = pId;
            if (pH == qH)
                height[pId] = pH + 1;
        } else {
            id[pId] = qId;
        }
        count--;
    }

    @Override
    public int find(int p) {
        while (id[p] != p)
            p = id[p];
        return p;
    }
}
