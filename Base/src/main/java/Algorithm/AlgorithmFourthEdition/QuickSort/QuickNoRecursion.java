package Algorithm.AlgorithmFourthEdition.QuickSort;

import Algorithm.AlgorithmFourthEdition.BagQueueAndStack.Stack;
import Algorithm.AlgorithmFourthEdition.Utils;

/**
 * Created by user-hfc on 2020/4/5.
 *
 * 2.3.20 非递归的快速排序。实现一个非递归的快速排序，使用一个循环来将
 * 弹出栈的子数组切分并将结果子数组重新压入栈。注意：先将较大的子数组压
 * 入栈，这样就可以保证栈最多只会有lgN个元素。
 *
 */
public class QuickNoRecursion {

    public void sort(int[] a) {
        Stack<Pair> parts = new Stack<>();
        parts.push(new Pair(0, a.length - 1));
        sort(a, parts);
    }

    protected void sort(int[] a, Stack<Pair> parts) {
        Pair p;
        while (!parts.isEmpty()) {
            p = parts.pop();
            int j = partition(a, p.lo, p.hi);
            if (p.hi > j+1) parts.push(new Pair(j+1, p.hi));
            if (j-1 > p.lo) parts.push(new Pair(p.lo, j-1));
        }
    }

    // 返回的是切分元素K的位置下标
    protected int partition(int[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        int v = a[lo];
        while (true) {
            // 此处使用<和>，而不是<=和>=，是为了避免在存在大量重复数据时，
            // 运行时间变为平方级别
            // 如果是<=和>=，在有大量重复数据时，第一个while循环会使i很接近hi
            // 即左右数组划分不平均
            while (a[++i] < v) if (i == hi) break;
            while (a[--j] > v) if (j == lo) break;

            if (i >= j) break;
            Utils.exchange(a, i, j);
        }

        // 交换完毕时，i和j处在边界处
        // j指向较小区块，i指向较大区块
        Utils.exchange(a, j, lo);
        return j;
    }

    public static class Pair {
        private int lo;
        private int hi;

        public Pair(int begin, int end) {
            lo = begin;
            hi = end;
        }
    }

    public static void main(String[] args) {
        int[] src = Utils.numGen(20);

        Utils.showResult(src);

        QuickNoRecursion quick = new QuickNoRecursion();
        quick.sort(src);

        Utils.showAscResult(src);
    }
}
