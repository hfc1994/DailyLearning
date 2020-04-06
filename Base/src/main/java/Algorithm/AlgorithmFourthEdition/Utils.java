package Algorithm.AlgorithmFourthEdition;

import java.util.Random;

/**
 * Created by user-hfc on 2020/3/28.
 */
public class Utils {

    private static Random random = new Random(System.currentTimeMillis());

    // 生成给定长度，且全部填充入100范围内随机整数的数组
    public static int[] numGen(int length) {
        return numGen(length, 100);
    }

    // 生成给定长度，且全部填充入指定范围以内的随机整数值
    public static int[] numGen(int length, int bound) {
        int[] src = new int[length];
        for (int i=0; i<length; i++) {
            src[i] = random.nextInt(bound);
        }
        return src;
    }

    // i号元素是否比j号元素小
    @SuppressWarnings("unchecked")
    public static boolean less(Comparable[] pq, int i, int j) {
        return pq[i].compareTo(pq[j]) < 0;
    }

    // 交换src数组内m和n位置的内容
    public static void exchange(int[] src, int m, int n) {
        int temp = src[m];
        src[m] = src[n];
        src[n] = temp;
    }
    public static void exchange(Comparable[] src, int m, int n) {
        Comparable temp = src[m];
        src[m] = src[n];
        src[n] = temp;
    }

    // 三种展示结果的方法
    public static void showResult(int[] src) {
        showAndJudgeResult(src, null);
    }
    public static void showAscResult(int[] src) {
        showAndJudgeResult(src, "asc");
    }
    public static void showDescResult(int[] src) {
        showAndJudgeResult(src, "desc");
    }

    private static void showAndJudgeResult(int[] src, String type) {
        for (int num : src)
            System.out.print(num + " ");
        System.out.println();

        if ("asc".equals(type)) System.out.println(ascOrder(src));
        else if ("desc".equals(type)) System.out.println(descOrder(src));
    }

    // 判断给定数组是否是递增的
    public static boolean ascOrder(int[] src) {
        for (int i=1; i<src.length; i++) {
            if (src[i] < src[i-1]) {
                System.out.println("--- 不是递增排序 ---");
                return false;
            }
        }
        return true;
    }

    // 判断给定数组是否是递减的
    public static boolean descOrder(int[] src) {
        for (int i=1; i<src.length; i++) {
            if (src[i] > src[i-1]) {
                System.out.println("--- 不是递减排序 ---");
                return false;
            }
        }
        return true;
    }
}
