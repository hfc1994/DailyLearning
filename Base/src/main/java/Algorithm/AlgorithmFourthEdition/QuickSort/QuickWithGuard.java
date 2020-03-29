package Algorithm.AlgorithmFourthEdition.QuickSort;

import Algorithm.AlgorithmFourthEdition.Utils;

/**
 * Created by user-hfc on 2020/3/29.
 *
 * 2.3.17 哨兵。修改算法2.5，去掉内循环while中的边界检查。由于切分元素本身
 * 就是一个哨兵（v不可能小于a[lo]），左侧边界的检查是多余的。要去掉另一个
 * 检查，可以在打乱数组后将数组的最大元素放在a[length-1]中。该元素永远不会
 * 移动（除非和相等的元素交换），可以在所有包含它的子数组中成为哨兵。注意：
 * 在处理内部子数组时，右子数组中最左侧的元素可以作为左子数组右边界的哨兵
 */
public class QuickWithGuard {

    public void sort(int[] a) {
        int len = a.length;
        int maxIndex = 0;
        int max = a[0];
        for (int i=1; i<len; i++) {
            if (a[i] > max) {
                maxIndex = i;
                max = a[i];
            }
        }

        // 把最大值放在最右边当哨兵
        if (maxIndex != len-1) {
            int temp = a[len - 1];
            a[len - 1] = a[maxIndex];
            a[maxIndex] = temp;
        }

        sort(a, 0, a.length - 1);
    }

    protected void sort(int[] a, int lo, int hi) {
        if (hi <= lo) return;
        int j = partition(a, lo, hi);
        // a[lo...j-1]的值小于等于a[j]，所以把a[j]当做做数组的哨兵
        sort(a, lo, j-1);
        sort(a, j+1, hi);
    }

    protected int partition(int[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        int v = a[lo];
        while (true) {
            // 删去循环体，不做边界判断
            // ++i会大于hi，但最多也只大1
            while (a[++i] < v);
            while (a[--j] > v);

            if (i >= j) break;
            Utils.exchange(a, i, j);
        }

        // 交换完毕时，i和j处在边界处
        // j指向较小区块，i指向较大区块
        Utils.exchange(a, j, lo);
        return j;
    }

    public static void main(String[] args) {
//        for (int i=0; i<100; i++) {
            int[] src = new int[20];
            Utils.numGen(src);

            Utils.showResult(src);

            QuickWithGuard quick = new QuickWithGuard();
            quick.sort(src);

            Utils.showAscResult(src);
        }
//    }
}
