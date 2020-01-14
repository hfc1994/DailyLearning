package Algorithm.AlgorithmFourthEdition.UnionFind;

/**
 * Created by user-hfc on 2020/1/13.
 *
 * 在QuickUnion的基础上，给每一个触点增加一个权重值。
 * 权重值表示这棵树（连通分量）的大小
 */
public class WeightQuickUnion extends AbstractUF {

    protected int[] score;  // 每个触点的权重

    public WeightQuickUnion(int N) {
        id = new int[N];
        score = new int[N];
        count = N;
        for (int i=0; i<N; i++) {
            id[i] = i;
            score[i] = 1;
        }
    }

    @Override
    public void union(int p, int q) {
        int pId = find(p);
        int qId = find(q);

        if (pId == qId)
            return;

        if (score[pId] >= score[qId]) {
            id[qId] = pId;
            score[pId] += score[qId];
        } else {
            id[pId] = qId;
            score[qId] += score[pId];
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
