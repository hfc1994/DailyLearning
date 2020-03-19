package Algorithm.AlgorithmFourthEdition.Sort;

/**
 * Created by user-hfc on 2020/3/12.
 *
 * 自顶向下的归并排序
 * 递归的把大数组拆分成小数组，然后在小数组上使用原地归并排序
 */
public class MergeUB extends Merge {

    @Override
    public void sort(int[] a) {
        aux = new int[a.length];
        sort(a, 0, a.length - 1);
    }

    protected void sort(int[] a, int lo, int hi) {
        if (lo >= hi)
            return;

        int mid = (lo + hi) / 2;

        sort(a, lo, mid);
        sort(a, mid + 1, hi);
        merge(a, lo, mid, hi);
    }

    public static void main(String[] args) {
        int[] demo = new int[] {43,21,3,6,87,45,65,90,34,23,8,15,94,26,72,5,21,17,63,33};

        MergeUB mergeUB = new MergeUB();
        mergeUB.sort(demo);

        for (int i=0; i<demo.length; i++) {
            System.out.print(demo[i]);
            System.out.print(" ");
        }
    }
}
