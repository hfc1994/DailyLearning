package Algorithm.AlgorithmFourthEdition.Sort;

/**
 * Created by user-hfc on 2020/3/19.
 *
 * 2.2.11 改进。实现2.2.2节所述的对归并排序的三项改进：加快小数组的排序速度，
 * 检测数组是否已经有序以及通过在递归中交换参数来避免数组复制。
 *
 * “递归中交换参数来避免数组复制”参考了答案
 */
public class MergeUBNew extends MergeUB {

    @Override
    public void sort(int[] a) {
        // 重点 aux是a的副本！！
        aux = a.clone();
        sort(aux, a, 0, a.length - 1);
    }

    // 是对dst进行排序
    protected void sort(int[] src, int[] dst, int lo, int hi) {
        // 加快小数组的排序数组
        // 数组足够小就进行插入排序
        if (hi - lo <= 4) {
            for (int i=lo; i<=hi; i++) {
                for (int j=i; j>lo && dst[j] < dst[j-1]; j--) {
                    int tmp = dst[j];
                    dst[j] = dst[j-1];
                    dst[j-1] = tmp;
                }
            }
            return;
        }

        int mid = (lo + hi) / 2;

        // 递归中交换参数来避免数组复制
        // 此处是对src进行排序
        sort(dst, src, lo, mid);
        sort(dst, src, mid + 1, hi);

        // 检测数组是否已经有序
        if (src[mid + 1] >= src[mid]) {
            // 会比merge稍快
            System.arraycopy(src, lo, dst, lo, hi - lo + 1);
            return;
        }

        // 此处把src的内容归并放到dst中
        merge(src, dst, lo, mid, hi);
    }

    // 对src的部分数据进行归并，结果存放着dst
    public void merge(int[] src, int[] dst, int lo, int mid, int hi) {
        int i=lo;
        int j=mid+1;
        // k从lo开始且小于等于hi，而不是小于a.length()
        for (int k=lo; k<=hi; k++) {
            if (i > mid) dst[k] = src[j++];
            else if (j > hi) dst[k] = src[i++];
            else if (src[i] > src[j]) dst[k] = src[j++];
            else dst[k] = src[i++];
        }
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
