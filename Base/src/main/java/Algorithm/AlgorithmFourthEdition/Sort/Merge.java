package Algorithm.AlgorithmFourthEdition.Sort;

/**
 * Created by user-hfc on 2020/3/12.
 *
 * 原地归并排序
 * 将两个不同的有序数组归并成一个更大的有序数组
 */
public class Merge {

    protected int[] aux;

    public void sort(int[] a) {
        aux = new int[a.length];
        int mid = (a.length - 1) / 2;
        merge(a, 0, mid, a.length - 1);
//        mergeWithoutCheck(a, 0, mid, a.length - 1);
    }

    // [lo, mid], [mid+1, hi]
    public void merge(int[] a, int lo, int mid, int hi) {
        for (int i=0; i<a.length; i++) {
            aux[i] = a[i];
        }

        int i=lo;
        int j=mid+1;
        // k从lo开始且小于等于hi，而不是小于a.length()
        for (int k=lo; k<=hi; k++) {
            if (i > mid) a[k] = aux[j++];
            else if (j > hi) a[k] = aux[i++];
            else if (aux[i] > aux[j]) a[k] = aux[j++];
            else a[k] = aux[i++];
        }
    }

    /**
     * 快速归并。实现一个merge()方法，按降序将a[]的后半部分复制到aux[]，然后
     * 将其归并回a[]中。这样就可以去掉内循环中检测某半边是否用尽的代码。
     * *注意：这样的排序产生的结果是不稳定的
     */
    public void mergeWithoutCheck(int[] a, int lo, int mid, int hi) {
        for (int i=0; i<=hi; i++) {
            if (i <= mid)
                aux[i] = a[i];
            else
                aux[i] = a[hi - i + mid + 1];
        }

        int i=lo;
        int j=hi;
        // k从lo开始且小于等于hi，而不是小于a.length()
        for (int k=lo; k<=hi; k++) {
            if (aux[i] > aux[j]) a[k] = aux[j--];
            else a[k] = aux[i++];
        }
    }

    public static void main(String[] args) {
        int[] demo = new int[] {1,4,9,12,34,45,54,65,128,133,3,7,16,23,26,45,56,129,156,157};

        Merge merge = new Merge();
        merge.sort(demo);

        for (int i=0; i<demo.length; i++) {
            System.out.print(demo[i]);
            System.out.print(" ");
        }
    }
}
