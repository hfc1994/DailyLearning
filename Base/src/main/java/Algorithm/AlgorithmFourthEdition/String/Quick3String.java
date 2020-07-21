package Algorithm.AlgorithmFourthEdition.String;

/**
 * Created by user-hfc on 2020/7/9.
 *
 * 三向字符串快速排序
 */
public class Quick3String {

    private static int charAt(String s, int d) {
        if (d<s.length())
            return s.charAt(d);
        else
            return -1;
    }

    public static void sort(String[] a) {
        sort(a, 0, a.length-1, 0);
    }

    private static void sort(String[] a, int lo, int hi, int d) {
        if (hi<=lo)
            return;
        int lt=lo;
        int gt=hi;
        int v = charAt(a[lo], d);
        int i=lo+1;
        while (i<=gt) {
            int t = charAt(a[i], d);
            if (t<v)
                exch(a, lt++, i++);
            else if (t>v)
                exch(a, i, gt--);
            else
                i++;
        }

        // a[lo...lt-1] < v = a[lt...gt] < a[gt+1...hi]
        sort(a, lo, lt-1, d);
        if (v>=0)
            sort(a, lt, gt, d+1);
        sort(a, gt+1, hi, d);
    }

    private static void exch(String[] a, int i, int j) {
        String tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    public static void main(String[] args) {
        String[] src = new String[] {
                "4PGC938",  "2IYE230",
                "3CI0720",  "1ICK750",
                "1OHV845",  "4JZY524",
                "1ICK750",  "3CI0720",
                "1OHV845",  "1OHV845",
                "2RLA629",  "2RLA629",
                "3ATW723"
        };

        sort(src);

        for (String str : src)
            System.out.print(str + " ");
        System.out.println();
    }
}
