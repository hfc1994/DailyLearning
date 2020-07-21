package Algorithm.AlgorithmFourthEdition.String;

import Algorithm.AlgorithmFourthEdition.Sort.Insertion;

/**
 * Created by user-hfc on 2020/7/9.
 *
 * 高位优先的字符串排序
 */
public class MSD {

    private static int R = 256; //  基数
    private static final int M = 3;    // 小数组的切换阈值
    private static String[] aux;    // 数据分类的辅助数组
    private static Insertion<String> insertion;

    private static int charAt(String s, int d) {
        if (d<s.length())
            return s.charAt(d);
        else
            return -1;
    }

    public static void sort(String[] a) {
        int N = a.length;
        aux = new String[N];
        sort(a, 0, N-1, 0);
    }

    private static void sort(String[] a, int lo, int hi, int d) {
        // 以第d个字符为键将a[lo]至a[hi]排序

        // 长度过小的数组直接进行插入排序
        if (hi <= lo + M) {
            insertionSort(a, lo, hi, d);
            return;
        }

        int[] count = new int[R+2]; // 计算频率
        // 只比价lo-hi之间的内容
        for (int i=lo; i<=hi; i++)
            count[charAt(a[i], d) + 2]++;

        for (int r=0; r<R+1; r++)   // 将频率转换为索引
            count[r+1] += count[r];

        for (int i=lo; i<=hi; i++)  // 数据分类
            aux[count[charAt(a[i], d) + 1]++] = a[i];

        for (int i=lo; i<=hi; i++) // 回写
            a[i] = aux[i-lo];

        // 递归的以每个字符为键进行排序
        for (int r=0; r<R; r++)
            sort(a, lo+count[r], lo+count[r+1]-1, d+1);
    }


    private static void insertionSort(String[] a, int lo, int hi, int d) {
        for (int i=lo; i<hi; i++) {
            for (int j=i; j>lo; j--) {
                if (charAt(a[j], d) < charAt(a[j-1], d)) {
                    String tmp = a[j];
                    a[j] = a[j-1];
                    a[j-1] = tmp;
                }
            }
        }
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
