package Algorithm.AlgorithmFourthEdition.Sort;

/**
 * Created by user-hfc on 2020/3/14.
 *
 * 自底向上的排序
 * 先按多个小数组间排序，然后多个中数组间排序...
 *
 */
public class MergeBU extends Merge {

    @Override
    public void sort(int[] a) {
        aux = new int[a.length];
        for (int step = 1;step < a.length;) {
            int lo, mid, hi;
            for (int index = 0; index < a.length - step; index = hi + 1) {
                lo = index;
                mid = index + step - 1;
                hi = index + 2 * step - 1;
                if (hi > a.length - 1)
                    hi = a.length - 1;
                this.merge(a, lo, mid, hi);
            }
            step = step * 2;
        }
    }

    public static void main(String[] args) {
        int[] demo = new int[] {43,21,3,6,87,45,65,90,34,23,8,15,94,26,72,5,21,17,63,33};

        MergeBU mergeBU = new MergeBU();
        mergeBU.sort(demo);

        for (int i=0; i<demo.length; i++) {
            System.out.print(demo[i]);
            System.out.print(" ");
        }
    }
}
