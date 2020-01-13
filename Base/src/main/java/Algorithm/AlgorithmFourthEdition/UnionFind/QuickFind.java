package Algorithm.AlgorithmFourthEdition.UnionFind;

/**
 * Created by user-hfc on 2020/1/13.
 *
 * 当且仅当id[p]等于id[q]时p和q是连通的
 */
public class QuickFind extends AbstractUF {

    public QuickFind(int N) {
        id = new int[N];
        count = N;
        for (int i=0; i<N; i++)
            id[i] = i;
    }

    @Override
    public void union(int p, int q) {
        int pId = find(p);
        int qId = find(q);

        if (pId == qId)
            return;

        for (int i=0; i<id.length; i++) {
            if (id[i] == qId)
                id[i] = pId;
        }
        count--;
    }

    @Override
    public int find(int p) {
        return id[p];
    }
}
