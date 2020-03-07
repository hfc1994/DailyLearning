package Algorithm.AlgorithmFourthEdition.Sort;

/**
 * Created by hfc on 2020/3/7.
 *
 * 插入排序
 */
public class Insertion<T extends Comparable<? super T>> implements Sort<T> {

    @Override
    public void sort(T[] a) {
        int N = a.length;
        for (int i=1; i<N; i++) {
            for (int j=i; j>0; j--) {
                if (less(a[j], a[j-1]))
                    exchange(a, j, j-1);
                else
                    break;
            }
        }
    }

    public static void main(String[] args) {
        String str = "sortexample";
        String[] strs = str.split("");

        Insertion<String> insertion = new Insertion<>();
        insertion.sort(strs);
        insertion.show(strs);
    }
}
