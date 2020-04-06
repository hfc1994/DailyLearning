package Algorithm.AlgorithmFourthEdition.PriorityQueue;

import Algorithm.AlgorithmFourthEdition.Utils;

/**
 * Created by user-hfc on 2020/4/6.
 */
public class MaxPQ<T extends Comparable<T>> {

    private T[] pq;     // 基于堆的完全按二叉树
    private int N = 0;  // 存储于pq[1...N]中，pq[0]没有使用

    @SuppressWarnings("unchecked")
    public MaxPQ(int maxN) {
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

    public T delMax() {
        T max = pq[1];        // 从根结点得到最大元素
        exchange(1, N--);   // 将其和最后一个结点交换
        pq[N+1] = null;       // 防止对象游离
        sink(1);           // 恢复堆的有序性
        return max;
    }

    // i号元素是否比j号元素小
    private boolean less(int i, int j) {
        return pq[i].compareTo(pq[j]) < 0;
    }

    private void exchange(int i, int j) {
        T temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
    }

    // 上浮
    private void swim(int k) {
        while (k > 1 && less(k/2, k)) {
            exchange(k/2, k);
            k = k/2;
        }
    }

    // 下沉
    private void sink(int k) {
        int j;
        while (2*k <= N) {
            j = 2*k;
            if (j < N && less(j, j+1)) j++;

            if (!less(k, j))
                break;

            exchange(k, j);
            k = j;
        }
    }

    public static void main(String[] args) {
        int[] src = Utils.numGen(20);

        MaxPQ<Integer> maxPQ = new MaxPQ<>(src.length);
        for (int data : src)
            maxPQ.insert(data);

        while (!maxPQ.isEmpty()) {
            System.out.print(maxPQ.delMax());
            System.out.print(" ");
        }
        System.out.println();
    }
}
