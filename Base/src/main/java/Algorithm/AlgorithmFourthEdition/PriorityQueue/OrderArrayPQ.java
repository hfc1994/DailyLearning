package Algorithm.AlgorithmFourthEdition.PriorityQueue;

import Algorithm.AlgorithmFourthEdition.Utils;

/**
 * Created by user-hfc on 2020/4/6.
 *
 * 2.4.3 用以下数据结构实现优先队列，支持插入元素和删除最大元素的操作：
 * 无序数组、有序数组、无序链表和链表。
 *
 * 有序数组
 * 简单实现，不考虑数据边界问题
 */
public class OrderArrayPQ<T extends Comparable> implements PQ<T> {

    private T[] pq;
    private int N = 0;

    @SuppressWarnings("unchecked")
    public OrderArrayPQ(int maxN) {
        pq = (T[]) new Comparable[maxN];
    }

    @Override
    public boolean isEmpty() {
        return N == 0;
    }

    @Override
    public int size() {
        return N;
    }

    @Override
    public void insert(T v) {
        pq[N++] = v;

        int index = N-1;
        while (index > 0) {
            if (Utils.less(pq, index-1, index)) break;
            Utils.exchange(pq, index-1, index);
            index--;
        }
    }

    @Override
    public T del() {
        T ret = pq[--N];
        pq[N] = null;
        return ret;
    }

    public static void main(String[] args) {
        int[] src = Utils.numGen(20);

        PQ<Integer> maxIntPQ = new OrderArrayPQ<>(src.length);
        for (int data : src)
            maxIntPQ.insert(data);

        int[] dst = new int[src.length];
        while (!maxIntPQ.isEmpty())
            dst[src.length - maxIntPQ.size()] = maxIntPQ.del();

        Utils.showDescResult(dst);
    }
}
