package Algorithm.AlgorithmFourthEdition.Sort;

import java.util.Random;

/**
 * Created by hfc on 2020/3/7.
 *
 * 插入排序
 */
public class Insertion<T extends Comparable<T>> implements Sort<T> {

    @Override
    public void sort(T[] a) {
        int N = a.length;
        for (int i=1; i<N; i++) {
            for (int j=i; j>0 && less(a[j], a[j-1]); j--) {
                exchange(a, j, j-1);
            }
        }
    }

    /**
     * 2.1.24 插入排序的哨兵 在插入排序的实现中先找出最小的元素并将其置于
     * 数组的最左边，这样就能去掉内循环的判断条件j>0。使用SortCompare来
     * 评估这种做法的效果。注意：这是一种常见的规避边界测试的方法，能够省略
     * 判断条件的元素通常被称为哨兵。
     */
    public void sortAfterFoundMin(T[] a) {
        int N = a.length;
        int min = 0;
        // 首先找出最小的
        for (int i=1; i<N; i++)
            min = less(a[min], a[i]) ? min : i;
        // 最小的放第一个
        if (min != 0)
            exchange(a, 0, min);

        for (int i=1; i<N; i++) {
            for (int j=i; less(a[j], a[j-1]); j--) {
                exchange(a, j, j-1);
            }
        }
    }

    /**
     * 2.1.25 不需要交换的插入排序 在插入排序的实现中使较大元素右移一位
     * 只需要访问一次数组（而不用exch()）。使用SortCompare来评估这种做
     * 法的效果。
     */
    public void sortWithLessExchange(T[] a) {
        int N = a.length;
        for (int i=1; i<N; i++) {
            T t = a[i];
            int j;
            for (j=i; j>0 && less(t, a[j-1]); j--) {
                a[j] = a[j-1];
            }
            a[j] = t;
        }
    }

    /**
     * 2.1.26 原始数据类型 编写一个能够处理int值的插入排序的新版本，比较
     * 它和正文中所给的实现（能够隐式地用自动装箱和拆箱转换Integer值并排序）
     * 地性能。
     */
    public void sortInt(int[] a) {
        int N = a.length;
        for (int i=1; i<N; i++) {
            for (int j=i; j>0 && a[j] < a[j-1]; j--) {
                int tmp = a[j];
                a[j] = a[j-1];
                a[j-1] = tmp;
            }
        }
    }

    public static void main(String[] args) {
        String str = "sortexample";
        String[] strs = str.split("");

        Insertion<String> insertion = new Insertion<>();
        insertion.sort(strs);
        insertion.show(strs);

//        // 2.1.26
//        int N = 1000;
//        int T = 100;
//        Insertion<Integer> insertion = new Insertion<>();
//        Random random = new Random(System.currentTimeMillis());
//
//        Integer[] aInteger;
//        int[] aInt;
//        int num;
//        long lInteger = 0L, lInt = 0L, lBegin = 0L;
//        for (int i=0; i<T; i++) {
//            aInteger = new Integer[N];
//            aInt = new int[N];
//            for (int j=0; j<N; j++) {
//                num = random.nextInt();
//                aInteger[j] = num;
//                aInt[j] = num;
//            }
//            lBegin = System.currentTimeMillis();
//            insertion.sort(aInteger);
//            lInteger += System.currentTimeMillis() - lBegin;
//            lBegin = System.currentTimeMillis();
//            insertion.sortInt(aInt);
//            lInt += System.currentTimeMillis() - lBegin;
//        }
//
//        System.out.println("sort = " + lInteger);
//        System.out.println("sortInt = " + lInt);
//        System.out.printf("For %d random Integer/int\n   sort is", N);
//        System.out.printf(" %.2f times faster than sortInt\n", lInt * 1.0 /lInteger);
    }
}
