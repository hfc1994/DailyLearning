package Algorithm.AlgorithmFourthEdition.PriorityQueue;

import Algorithm.AlgorithmFourthEdition.Utils;

/**
 * Created by user-hfc on 2020/4/12.
 *
 * 基于堆面向最小元素的优先队列
 */
public class MinPQ<T extends Comparable> implements PQ<T> {

    private T[] pq;     // 基于堆的完全按二叉树
    private int N = 0;  // 存储于pq[1...N]中，pq[0]没有使用

    @SuppressWarnings("unchecked")
    public MinPQ(int maxN) {
        pq = (T[]) new Comparable[maxN + 1];
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void insert(T v) {
        pq[++N] = v;
        swim(N);
    }

    public T delMin() {
        return del();
    }

    @Override
    public T del() {
        T min = pq[1];        // 从根结点得到最小元素
        Utils.exchange(pq, 1, N--);   // 将其和最后一个结点交换
        pq[N+1] = null;       // 防止对象游离

        sink(1);           // 恢复堆的有序性
        return min;
    }

    // 上浮
    private void swim(int k) {
        while (k > 1 && Utils.less(pq, k, k / 2)) {
            Utils.exchange(pq, k, k / 2);
            k = k / 2;
        }
    }

    // 下沉
    private void sink(int k) {
        int j;
        while (2 * k <= N) {
            j = 2 * k;
            if (j < N && Utils.less(pq, j + 1, j)) j++;

            if (!Utils.less(pq, j, k))
                break;

            Utils.exchange(pq, j, k);
            k = j;
        }
    }

    public static void main(String[] args) {
        int[] src = Utils.numGen(20);

        MinPQ<Integer> minIntPQ = new MinPQ<>(src.length);
        for (int data : src)
            minIntPQ.insert(data);

        int[] dst = new int[src.length];
        while (!minIntPQ.isEmpty())
            dst[src.length - minIntPQ.size()] = minIntPQ.delMin();

        Utils.showAscResult(dst);

    }
}