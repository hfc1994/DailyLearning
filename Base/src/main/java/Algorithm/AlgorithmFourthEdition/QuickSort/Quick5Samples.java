package Algorithm.AlgorithmFourthEdition.QuickSort;

import Algorithm.AlgorithmFourthEdition.Utils;

import java.util.Random;

/**
 * Created by user-hfc on 2020/3/30.
 *
 * 2.3.19 五取样切分。实现一种基于随机抽取子数组中5个元素并取中位数
 * 进行切分的快速排序。将取样元素放在数组的一侧以保证只有中位数元素
 * 参与了切分。
 *
 * TODO: 实现随机获取5个
 */
public class Quick5Samples extends Quick3Samples {

    private Random random = new Random();

    @Override
    protected void sort(int[] a, int lo, int hi) {
        if (hi <= lo) return;

        if (hi - lo >= 5) {
//            int[] indexs = new int[5];
//            for (int i=0; i<5; i++) {
//                indexs
//            }
            // TODO: 2020/3/30 随机？
            int mid = findMid(a, lo, lo + 4);
            Utils.exchange(a, lo, mid);
        }

        int j = partition(a, lo, hi);
        sort(a, lo, j-1);
        sort(a, j+1, hi);
    }

    public static void main(String[] args) {
        int[] src = Utils.numGen(20);

        Utils.showResult(src);

        Quick5Samples quick = new Quick5Samples();
        quick.sort(src);

        Utils.showAscResult(src);
    }
}
