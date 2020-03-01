package Algorithm.AlgorithmFourthEdition.UnionFind;

/**
 * Created by hfc on 2020/3/1.
 *
 * 1.5.20 动态生长。使用链表或大小可变的数组实现加权quick-union
 * 算法，去掉需要预先知道对象数量的限制。为API添加一个新方法newSite(),
 * 它应该返回一个类型为int的标识符。
 *
 * todo 实现的不优雅...
 */
public class DynamicWeightQuickUnion extends AbstractUF {

    private int[] height;
    private int defaultSize = 8;

    // 进行连接的最大值
    private int maxP = 0;

    @Override
    public void union(int p, int q) {
        if (id == null)
            resize(0);

        int len = p >= q ? p : q;
        maxP = maxP > len ? maxP : len;
        if (len+1 > id.length)
            resize(len+1);

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
        count++;
    }

    // 扩容
    private void resize(int position) {
        // 第一次初始化
        if (id == null) {
            id = new int[defaultSize];
            height = new int[defaultSize];
            initArray(id, height, 0);
            count = 0;
            return;
        }

        int[] oldId = id;
        int[] oldHeight = height;
        while (id.length < position) {
            id = new int[id.length * 2];
            height = new int[height.length * 2];
        }

        // 避免出现多个拷贝
        if (oldId.length != id.length) {
            System.arraycopy(oldId, 0, id, 0, oldId.length);
            System.arraycopy(oldHeight, 0, height, 0, oldHeight.length);
            initArray(id, height, oldHeight.length);
        }
    }

    private void initArray(int[] id, int[] weight, int beginIndex) {
        for (int i=beginIndex; i<id.length; i++) {
            id[i] = i;
            weight[i] = 0;
        }
    }

    @Override
    public int find(int p) {
        while (id[p] != p)
            p = id[p];
        return p;
    }

    @Override
    public boolean connected(int p, int q) {
        int len = p > q ? p : q;
        // p和q是从0开始的
        if (id == null || len + 1 > id.length)
            return false;
        return super.connected(p, q);
    }

    @Override
    public int count() {
        return maxP + 1 - count;
    }

    /**
     * 返回数组大小
     */
    public int newSite() {
        if (id == null)
            return 0;

        return id.length;
    }
}
