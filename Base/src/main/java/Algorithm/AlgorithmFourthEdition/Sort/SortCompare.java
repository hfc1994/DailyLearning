package Algorithm.AlgorithmFourthEdition.Sort;

import java.util.Random;

/**
 * Created by hfc on 2020/3/7.
 */
public class SortCompare {

    private Selection<Double> selection = new Selection<>();
    private Insertion<Double> insertion = new Insertion<>();

    // 固定的种子，保证每组的随机生成都是固定的
    private Random random = new Random(System.currentTimeMillis());

    public long time(String alg, Double[] a) {
        long begin = System.currentTimeMillis();
        switch (alg) {
            case "Selection":
                selection.sort(a);
                break;
            case "Insertion":
                insertion.sort(a);
                break;
            default:
        }
        return System.currentTimeMillis() - begin;
    }

    public long timeRandomInput(String alg, int N, int T) {
        // 使用算法alg将T个长度为N的数组排序
        long total = 0L;
        Double[] a = new Double[N];
        for (int t=0; t<T; t++) {
            for (int i=0; i<N; i++) {
                a[i] = random.nextDouble();
            }
            total += time(alg, a);
        }
        return total;
    }

    public static void main(String[] args) {
        String alg1 = "Insertion";
        String alg2 = "Selection";

//        String alg1 = "Selection";
//        String alg2 = "Insertion";

        int N = 10000;
        int T = 100;

        SortCompare sc = new SortCompare();
        double t1 = sc.timeRandomInput(alg1, N, T);
        double t2 = sc.timeRandomInput(alg2, N, T);

        System.out.printf("For %d random Doubles\n   %s is", N, alg1);
        System.out.printf(" %.2f times faster than %s\n", t2/t1, alg2);
    }
}
