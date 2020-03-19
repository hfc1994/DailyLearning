package Algorithm.AlgorithmFourthEdition.Sort;

/**
 * Created by user-hfc on 2020/3/19.
 *
 * 2.2.11 改进。实现2.2.2节所述的对归并排序的三项改进：加快小数组的排序速度，
 * 检测数组是否已经有序以及通过在递归中交换参数来避免数组复制。
 *
 * todo 通过在递归中交换参数来避免数组复制
 */
public class MergeUBNew extends MergeUB {

    @Override
    public void sort(int[] a) {
        aux = new int[a.length];
        sort(a, 0, a.length - 1);
    }

    protected void sort(int[] a, int lo, int hi) {
        // 加快小数组的排序数组
        // 数组足够小就进行插入排序
        if (hi - lo <= 4) {
            for (int i=lo; i<=hi; i++) {
                for (int j=i; j>lo; j--) {
                    if (a[j] < a[j-1]) {
                        int tmp = a[j];
                        a[j] = a[j-1];
                        a[j-1] = tmp;
                    }
                }
            }
            return;
        }

        int mid = (lo + hi) / 2;

        sort(a, lo, mid);
        sort(a, mid + 1, hi);

        // 检测数组是否已经有序
        if (a[mid] > a[mid + 1])
            merge(a, lo, mid, hi);
    }


    @Override
    public void merge(int[] a, int lo, int mid, int hi) {
        super.merge(a, lo, mid, hi);
    }

    public static void main(String[] args) {
        int[] demo = new int[] {43,21,3,6,87,45,65,90,34,23,8,15,94,26,72,5,21,17,63,33};

        MergeUBNew MergeUBNew = new MergeUBNew();
        MergeUBNew.sort(demo);

        for (int i=0; i<demo.length; i++) {
            System.out.print(demo[i]);
            System.out.print(" ");
        }
    }
}
