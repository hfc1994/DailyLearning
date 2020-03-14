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
