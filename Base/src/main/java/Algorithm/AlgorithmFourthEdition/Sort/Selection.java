package Algorithm.AlgorithmFourthEdition.Sort;

/**
 * Created by hfc on 2020/3/7.
 *
 * 选择排序
 */
public class Selection<T extends Comparable<? super T>> implements Sort<T> {

    @Override
    public void sort(T[] a) {
        int N = a.length;
        for (int i=0; i<N-1; i++) {
            int min = i;
            for (int j=i+1; j<N; j++) {
                if (less(a[j], a[min]))
                    min = j;
            }
            exchange(a, i, min);
        }
    }

    public static void main(String[] args) {
        String str = "sortexample";
        String[] strs = str.split("");

        Selection<String> selection = new Selection<>();
        selection.sort(strs);
        selection.show(strs);
    }
}
