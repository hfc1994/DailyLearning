package Algorithm.AlgorithmFourthEdition.QuickSort;

import Algorithm.AlgorithmFourthEdition.Utils;

/**
 * Created by user-hfc on 2020/3/29.
 *
 * 三向切分的快速排序
 */
public class Quick3Way {

    public void sort(int[] a) {
        sort(a, 0, a.length - 1);
    }

    protected void sort(int[] a, int lo, int hi) {
        if (hi <= lo) return;

        int v = a[lo];
        int lt = lo, i = lo+1, gt = hi;
        while (i <= gt) {
            // 使用lt++而不是++lt，则a[lo]也会被处理，就不需要在最后面把a[lo]单独处理一次
            if (a[i] < v) Utils.exchange(a, lt++, i++);
            else if (a[i] == v) i++;
            else Utils.exchange(a, gt--, i);
        }
        // 使用lt++，则可省略这一步
//        Utils.exchange(a, lo, lt);

        sort(a, lo, lt-1);
        sort(a, gt+1, hi);
    }

    public static void main(String[] args) {
        int[] src = Utils.numGen(20);

        Utils.showResult(src);

        Quick3Way quick = new Quick3Way();
        quick.sort(src);

        Utils.showAscResult(src);
    }
}
