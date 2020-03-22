package Algorithm.AlgorithmFourthEdition.Sort;

/**
 * Created by user-hfc on 2020/3/22.
 *
 * 2.2.16 自然的归并排序。编写一个自底向上的归并排序，当需要将两个子数组排序时
 * 能够利用数组中已经有序的部分。首先找到一个有序的子数组（移动指针直到当前元素
 * 比上一个元素小为止）。然后再找出另一个并将它们归并。根据数组大小和数组中递增
 * 子数组的最大长度分析算法的运行时间。
 */
public class MergeNatural {

    private int[] aux;

    public void sort(int[] src) {
        aux = new int[src.length];

        int lo = 0, mid, hi;
        while (lo != src.length - 1) {
            lo = 0;
            mid = -1;
            boolean ordered = true;
            int i = 1;
            for (; i<src.length; i++) {
                if (src[i-1] > src[i]) {
                    ordered = false;
                    if (mid == -1) {
                        mid = i-1;
                    } else {
                        hi = i-1;
                        merge(src, lo, mid, hi);
                        lo = i;
                        mid = -1;
                        if (i == src.length - 1) {
                            // 此时说明是最后一个，且是单独的
                            lo = 0;
                            break;
                        }
                    }
                }
            }

            if (ordered) {
                lo = src.length - 1;
            } else if (i == src.length && mid != -1) {
                // i == src.length表示指针已经移到末位
                // mid != -1表示前面已经有一个排好序的数组
                // 两个条件结合，说明mid之后的是一个排好序的数组，且是第二个
                // 所以就和前面的数组进行归并
                hi = i - 1;
                merge(src, lo, mid, hi);
            }
        }
    }

    private void merge(int[] src, int lo, int mid, int hi) {
        for (int i=0; i<src.length; i++) {
            aux[i] = src[i];
        }

        int i=lo;
        int j=mid+1;
        // k从lo开始且小于等于hi，而不是小于a.length()
        for (int k=lo; k<=hi; k++) {
            if (i > mid) src[k] = aux[j++];
            else if (j > hi) src[k] = aux[i++];
            else if (aux[i] > aux[j]) src[k] = aux[j++];
            else src[k] = aux[i++];
        }
    }

    public static void main(String[] args) {
        int[] demo = new int[] {43,21,3,6,87,45,65,90,34,23,8,15,94,26,72,5,21,17,63,33};

        MergeNatural mn = new MergeNatural();
        mn.sort(demo);
        for (int num : demo) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
}
