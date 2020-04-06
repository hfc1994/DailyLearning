package Algorithm.AlgorithmFourthEdition.PriorityQueue;

import Algorithm.AlgorithmFourthEdition.Utils;

/**
 * Created by user-hfc on 2020/4/6.
 *
 * -----------------------------------------------------
 * public class MaxPQ<Key extends Comparable<Key>
 * -----------------------------------------------------
 *          MaxPQ()             创建一个优先队列
 *          MaxPQ(int max)      创建一个初始容量为max的优先队列
 *          MaxPQ(Key[] a)      用a[]中的元素创建一个优先队列
 * void     Insert(Key v)       向优先队列中插入一个元素
 * Key      max()               返回最大元素
 * Key      delMax()            删除并返回最大元素
 * boolean  isEmpty()           返回队列是否为空
 * int      size()              返回优先队列中的元素个数
 * -----------------------------------------------------
 *
 * 基于堆的优先队列
 *
 */
public class MaxPQ<T extends Comparable> implements PQ<T> {

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
        Utils.exchange(pq, 1, N--);   // 将其和最后一个结点交换

        // pq[N+1] = null;       // 防止对象游离
        pq[N+1] = pq[N];       // 2.4.13 想办法在sink()中避免检查j < N


        sink(1);           // 恢复堆的有序性
        return max;
    }

    // 上浮
    private void swim(int k) {
        while (k > 1 && Utils.less(pq, k/2, k)) {
            Utils.exchange(pq, k/2, k);
            k = k/2;
        }
    }

    // 下沉
    private void sink(int k) {
        int j;
        while (2*k <= N) {
            j = 2*k;

            // if (j < N && Utils.less(pq, j, j+1)) j++;
            if (Utils.less(pq, j, j+1)) j++;    // 2.4.13 想办法在sink()中避免检查j < N

            if (!Utils.less(pq, k, j))
                break;

            Utils.exchange(pq, k, j);
            k = j;
        }
    }

    private void showContent() {
        for (int i=1; i<pq.length; i++) {
            System.out.print(pq[i]);
            System.out.print(" ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] src = Utils.numGen(20);

        MaxPQ<Integer> maxIntPQ = new MaxPQ<>(src.length);
        for (int data : src)
            maxIntPQ.insert(data);

        int[] dst = new int[src.length];
        while (!maxIntPQ.isEmpty())
            dst[src.length - maxIntPQ.size()] = maxIntPQ.delMax();

        Utils.showDescResult(dst);

        /**
         * 2.4.5 将E A S Y Q U E S T I O N顺序插入一个面向最大元素的堆中，给出结果
         */
//        String strSrc2 = "E A S Y Q U E S T I O N";
//        String[] arraySrc2 = strSrc2.split(" ");
//        MaxPQ<String> maxStrPQ2 = new MaxPQ<>(arraySrc2.length);
//        for (String str : arraySrc2)
//            maxStrPQ2.insert(str);
//        maxStrPQ2.showContent();

        /**
         * 2.4.6 按照练习2.4.1的规则，用序列P R I O * R * * I * T * Y * * * Q U E * * * U * E
         * 操作一个初始为空的面向最大元素的堆，给出每次操作后堆的内容。
         */
//        String strSrc = "P R I O * R * * I * T * Y * * * Q U E * * * U * E";
//        String[] arraySrc = strSrc.split(" ");
//        MaxPQ<String> maxStrPQ = new MaxPQ<>(arraySrc.length);
//        for (String str : arraySrc) {
//            if ("*".equals(str)) {
//                System.out.print(maxStrPQ.delMax());
//                System.out.print(" ");
//            } else {
//                maxStrPQ.insert(str);
//            }
//        }
//        System.out.println();
    }
}
