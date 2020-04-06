package Algorithm.AlgorithmFourthEdition.QuickSort;

import Algorithm.AlgorithmFourthEdition.Utils;

/**
 * Created by user-hfc on 2020/3/29.
 *
 * 2.3.4。给出一段代码将已知只有两种主键值的数组排序
 *
 * 从小到大，则一直把小的数往左边交换
 */
public class Quick2Keys {

    public void sort(int[] a) {
        sort(a, 0, a.length - 1);
    }

    protected void sort(int[] a, int lo, int hi) {
        if (hi <= lo) return;

        int j=lo+1;
        int i=j;

        // a[i]==a[j]表明两个指针指在同一个地方
        while (j <= hi) {
            if (a[j] == a[lo]) {
                if (a[i] != a[j]) Utils.exchange(a, i, j);
                i++;
            } else if (a[j] > a[lo]) {  // 表明a[lo]是偏小的值
                if (a[i] != a[j]) Utils.exchange(a, i++, j);
            } else {    // 表明a[lo]是偏大的值
                Utils.exchange(a, lo, j);
                i=lo+1;
            }
            j++;
        }
    }

    public static void main(String[] args) {
        for (int i=0; i<100; i++) {
            int[] src = Utils.numGen(50, 2);

            Utils.showResult(src);

            Quick2Keys quick = new Quick2Keys();
            quick.sort(src);

            Utils.showAscResult(src);
        }
    }
}
