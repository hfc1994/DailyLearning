package Algorithm.AlgorithmFourthEdition.UnionFind;

/**
 * Created by user-hfc on 2020/1/13.
 *
 * 每个触点所对应的id[]元素都是同一个分量中的另一个触点的名称（也可能是它自己），
 * 当链接指向自己时，这个触点为根触点。
 */
public class QuickUnion extends AbstractUF {

    public QuickUnion(int N) {
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

        id[qId] = pId;
        count--;
    }

    @Override
    public int find(int p) {
        while (id[p] != p)
            p = id[p];
        return p;
    }
}
