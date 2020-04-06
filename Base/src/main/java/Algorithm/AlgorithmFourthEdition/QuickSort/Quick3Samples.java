package Algorithm.AlgorithmFourthEdition.QuickSort;

import Algorithm.AlgorithmFourthEdition.Utils;

/**
 * Created by user-hfc on 2020/3/29.
 *
 * 2.3.18 三取样切分。为快速排序实现正文所述的三取样切分（参见2.3.3.2节）。
 */
public class Quick3Samples {

    public void sort(int[] a) {
        sort(a, 0, a.length - 1);
    }

    protected void sort(int[] a, int lo, int hi) {
        if (hi <= lo) return;

        if (hi - lo >= 3) {
            int mid = findMid(a, lo, lo + 2);
            Utils.exchange(a, lo, mid);
        }

        int j = partition(a, lo, hi);
        sort(a, lo, j-1);
        sort(a, j+1, hi);
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

    protected static int findMid(int[] src, int lo, int hi) {
        int[] samples = new int[hi -lo + 1];
        for (int i=0; i<samples.length; i++)
            samples[i] = lo + i;

        for (int i=0; i<samples.length-1; i++) {
            for (int j=i+1; j<samples.length; j++) {
                if (src[samples[j]] < src[samples[i]])
                    Utils.exchange(samples, i, j);
            }
        }
        return samples[(hi - lo)/2];
    }

    public static void main(String[] args) {
        int[] src = Utils.numGen(20);

        Utils.showResult(src);

        Quick3Samples quick = new Quick3Samples();
        quick.sort(src);

        Utils.showAscResult(src);
    }
}
