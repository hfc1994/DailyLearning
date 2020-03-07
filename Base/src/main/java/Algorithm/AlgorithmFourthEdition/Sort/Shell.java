package Algorithm.AlgorithmFourthEdition.Sort;

/**
 * Created by hfc on 2020/3/7.
 *
 * 希尔排序
 */
public class Shell<T extends Comparable<? super T>> implements Sort<T> {

    @Override
    public void sort(T[] a) {
        int N = a.length;
        int h = 1;
        while (h < N/3) h = 3*h + 1;
        while (h >= 1) {
            for (int i=h; i<N; i++) {
                for (int j=i; j>=h; j-=h) {
                    if (less(a[j], a[j-h]))
                        exchange(a, j, j-h);
                }
            }
            h = h/3;
        }
    }

    public static void main(String[] args) {
        String str = "sortexample";
        String[] strs = str.split("");

        Shell<String> shell = new Shell<>();
        shell.sort(strs);
        shell.show(strs);
    }
}
