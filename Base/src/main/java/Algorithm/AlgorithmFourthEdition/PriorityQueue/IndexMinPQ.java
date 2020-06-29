package Algorithm.AlgorithmFourthEdition.PriorityQueue;

import Algorithm.AlgorithmFourthEdition.Utils;

/**
 * Created by user-hfc on 2020/6/23.
 *
 * 关联索引的泛型最小元素优先队列
 *
 * 2.4.33 索引优先队列的实现。
 */
public class IndexMinPQ<T extends Comparable<T>> implements PQ<T> {

    private int[] pq;   // 基于堆的完全按二叉树，索引二叉堆，实际存的是对应在keys里的索引值
    private int[] qp;   // 逆序，qp[pq[i]] = pq[qp[i]] = i
    private T[] keys;  // 有优先级之分的元素
    private int N = 0;  // pq中的元素数量，存储于pq[1...N]中，pq[0]没有使用

    @SuppressWarnings("unchecked")
    public IndexMinPQ(int maxN) {
        pq =  new int[maxN + 1];
        qp = new int[maxN + 1];
        keys = (T[]) new Comparable[maxN + 1];
        for (int i=0; i<=maxN; i++)
            qp[i] = -1;
    }

    /*
     * 上浮
     */
    public void swim(int index) {
        while (index > 1) {
            if (Utils.less(keys, pq[index], pq[index/2]))
                exchange(index, index/2);
            index = index / 2;
        }
    }

    /*
     * 下沉
     */
    public void sink(int index) {
        int target;
        while (N >= 2 * index) {
            target = 2 * index;
            if (N >= target + 1 && Utils.less(keys, pq[target + 1], pq[target]))
                target = target + 1;

            if (N >= target && Utils.less(keys, pq[target], pq[index]))
                exchange(target, index);

            index = target;
        }
    }

    private void exchange(int m, int n) {
        int temp = pq[m];
        pq[m] = pq[n];
        pq[n] = temp;
        qp[pq[m]] = m;
        qp[pq[n]] = n;
    }

    @Override
    public void insert(T v) {
        // 不考虑insert(k, v) 可能会导致索引非连续
        N++;
        keys[N] = v;
        pq[N] = N;
        qp[N] = N;
        swim(N);
    }

    /*
     * 插入一个元素，将它和索引k相关联
     */
    public void insert(int k, T v) {
        N++;
        qp[k] = N;  // 叶子节点并非一定要从左到右排满
        pq[N] = k;
        keys[k] = v;
        swim(N);
    }

    @Override
    public T del() {
        T min = keys[pq[1]];
        exchange(1, N--);
        sink(1);
        keys[pq[N+1]] = null;
        qp[pq[N+1]] = -1;
        return min;
    }

    public T delMin() {
        return del();
    }

    public int delMinGetIndex() {
        int indexOfMin = pq[1];
        del();
        return indexOfMin;
    }

    /*
     * 是否存在索引为k的元素
     */
    public boolean contains(int k) {
        return qp[k] != -1;
    }

    /*
     * 返回最小元素
     */
    public T min() {
        return keys[pq[1]];
    }

    @Override
    public boolean isEmpty() {
        return N == 0;
    }

    @Override
    public int size() {
        return N;
    }

    /**
     * 2.4.34 索引优先队列的实现（附加操作）。向练习2.4.33的实现中添加
     * minIndex()、change()和delete()方法。
     */
    /*
     * 返回最小元素的索引
     */
    public int minIndex() {
        return pq[1];
    }

    /*
     * 将索引为k的元素设为v
     */
    public void change(int k, T v) {
        keys[k] = v;

        // 可能需要上浮，也可能需要下沉
        swim(qp[k]);
        sink(qp[k]);
    }

    /*
     * 删去索引k及其相关联的元素
     */
    public void delete(int k) {
        int index = qp[k];
        exchange(index, N--);
        swim(index);
        sink(index);
        keys[k] = null;
        qp[k] = -1;
    }

    public static void main(String[] args) {
        int[] src = Utils.numGen(10);

        IndexMinPQ<Integer> indexMinPQ = new IndexMinPQ<>(src.length);
        for (int data : src)
            indexMinPQ.insert(data);

        int[] dst = new int[src.length];
        while (!indexMinPQ.isEmpty())
            dst[src.length - indexMinPQ.size()] = indexMinPQ.delMin();

        Utils.showAscResult(dst);
    }
}
