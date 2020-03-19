package Algorithm.AlgorithmFourthEdition.Sort;

/**
 * Created by user-hfc on 2020/3/19.
 * 
 * 2.2.12 次线性额额外空间。用大小M将数组分为N/M块（简单起见，
 * 设M是N的约数），实现一个归并方法，使之所需的额外空间减少到
 * max(M, N/M)：(i)可以先将一个块看做一个元素，将块的第一个
 * 元素作为块的主键，用选择排序将块排序；(ii)遍历数组，将第
 * 一块和第二块归并，完成后将第二块和第三块归并，等等。
 */
public class MergePractise {
    
    private int M = 5;
    
    public void sort(int[] a, int lo, int hi) {
        for (int i=lo; i<=hi; i++) {
            for (int j=i; j>lo; j--) {
                if (a[j] < a[j-1]) {
                    int tmp = a[j];
                    a[j] = a[j-1];
                    a[j-1] = tmp;
                }
            }
        }
    }
    
    public void merge(int[] a, int lo, int mid, int hi) {
        // TODO: 2020/3/19  
    }
}
