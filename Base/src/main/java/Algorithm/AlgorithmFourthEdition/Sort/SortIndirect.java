package Algorithm.AlgorithmFourthEdition.Sort;

/**
 * Created by user-hfc on 2020/3/22.
 *
 * 2.2.20 间接排序。编写一个不改变数组的归并排序，它返回一个int[]数组perm，
 * 其中perm[i]的值是原数组中第i小的元素的位置。
 *
 */
public class SortIndirect {

    public int[] sort(int[] src) {
        int[] perm = new int[src.length];
        for (int i=0; i<perm.length; i++)
            perm[i] = i;

        // 选择排序的变种
        int minIndex;
        for (int i=0; i<perm.length-1; i++) {
            minIndex = i;
            for (int j=i+1; j<perm.length; j++) {
                if (src[perm[minIndex]] > src[perm[j]])
                    minIndex = j;
            }
            int tmp = perm[i];
            perm[i] = perm[minIndex];
            perm[minIndex] = tmp;
        }

        return perm;
    }

    public static void main(String[] args) {
        int[] demo = new int[] {43,21,3,6,87,45,65,90,34,23,8,15,94,26,72,5,21,17,63,33};

        SortIndirect si = new SortIndirect();
        int[] perm = si.sort(demo);
        for (int index : perm) {
            System.out.print(index + "[" + demo[index] + "] ");
        }
        System.out.println();
    }
}
