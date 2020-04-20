package Algorithm.AlgorithmFourthEdition.PriorityQueue;

import Algorithm.AlgorithmFourthEdition.Utils;

/**
 * Created by user-hfc on 2020/4/12.
 *
 * 2.4.29 同时面向最大和最小元素的优先队列。设计一个数据类型，支持如下
 * 操作：插入元素、删除最大元素、删除最小元素（所需时间均为对数级别），
 * 已经找到最大元素、找到最小元素（所需时间均为常数级别）。提示：用两个堆。
 */
public class MaxMinPQ<T extends Comparable>{

    private T[] minPQ;
    private T[] maxPQ;
    private int N;

    @SuppressWarnings("unchecked")
    public MaxMinPQ(int maxN) {
        minPQ = (T[]) new Comparable[maxN + 1];
        maxPQ = (T[]) new Comparable[maxN + 1];
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void insert(T v) {
        maxPQ[++N] = v;
        minPQ[N] = v;
        swim(maxPQ, N, true);
        swim(minPQ, N, false);
    }

    public T delMax() {
        T max = maxPQ[1];
        Utils.exchange(maxPQ, 1, N--);   // 将其和最后一个结点交换
        maxPQ[N+1] = maxPQ[N];              // 想办法在sink()中避免检查j < N
        sink(maxPQ, 1, true);           // 恢复堆的有序性

        // 把min堆中对应的数值也删去
        int index = 0;
        for (int i=1; i<minPQ.length; i++) {
            if (minPQ[i].compareTo(max) == 0) index = i;
        }
        minPQ[index] = minPQ[N+1];
        swim(minPQ, index, false);  // 可能会比父节点小

        return max;
    }

    public T delMin() {
        T min = minPQ[1];
        Utils.exchange(minPQ, 1, N--);   // 将其和最后一个结点交换
        minPQ[N+1] = minPQ[N];              // 想办法在sink()中避免检查j < N
        sink(minPQ, 1, false);           // 恢复堆的有序性

        // 把max堆中对应的数值也删去
        int index = 0;
        for (int i=1; i<maxPQ.length; i++) {
            if (maxPQ[i].compareTo(min) == 0) index = i;
        }
        maxPQ[index] = maxPQ[N+1];
        sink(maxPQ, index/2, true); // 可能会比父节点大

        return min;
    }

    public T findMax() {
        return maxPQ[1];
    }

    public T findMin() {
        return minPQ[1];
    }

    // 上浮
    private void swim(T[] pq, int k, boolean max) {
        int i,j;
        while (k > 1) {
            i = max ? k/2 : k;
            j = max ? k : k/2;

            if (!Utils.less(pq, i, j)) break;

            Utils.exchange(pq, i, j);
            k = k/2;
        }
    }

    // 下沉
    private void sink(T[] pq, int k, boolean max) {
        int i, j;
        while (2*k <= N) {
            i = max ? k : 2*k;
            j = max ? 2*k : k;

            if (max && Utils.less(pq, j, j+1)) j++;
            else if (!max && Utils.less(pq, i+1, i)) i++;

            if (!Utils.less(pq, i, j)) break;

            Utils.exchange(pq, i, j);
            k = max ? j : i;
        }
    }

    public static void main(String[] args) {
        int[] src = Utils.numGen(20);
        MaxMinPQ<Integer> maxMinIntPQ = new MaxMinPQ<>(src.length);

        for (int data : src)
            maxMinIntPQ.insert(data);
        int[] dstMax = new int[src.length];
        while (!maxMinIntPQ.isEmpty())
            dstMax[src.length - maxMinIntPQ.size()] = maxMinIntPQ.delMax();

        for (int data : src)
            maxMinIntPQ.insert(data);
        int[] dstMin = new int[src.length];
        while (!maxMinIntPQ.isEmpty())
            dstMin[src.length - maxMinIntPQ.size()] = maxMinIntPQ.delMin();

        Utils.showDescResult(dstMax);
        Utils.showAscResult(dstMin);
    }
}
