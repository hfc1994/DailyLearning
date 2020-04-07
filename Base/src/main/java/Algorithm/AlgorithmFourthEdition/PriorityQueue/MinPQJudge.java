package Algorithm.AlgorithmFourthEdition.PriorityQueue;

/**
 * Created by user-hfc on 2020/4/7.
 *
 * 2.4.15 设计一个程序，在线性时间内检测数组pq[]是否是一个
 * 面向最小元素的堆
 */
public class MinPQJudge<T extends Comparable> {

    public boolean judge(T[] src) {
        return judge(src, 1);
    }

    public boolean judge(T[] src, int index) {
        if (index >= src.length)
            return true;

        boolean left = true, right = true;
        if (2 * index < src.length) {
            left = judge(src, 2*index);
            left = !(src[index].compareTo(src[2*index])>0) && left;
        }
        if (2 * index + 1 < src.length) {
            right = judge(src, 2*index+1);
            right = !(src[index].compareTo(src[2*index+1])>0) && right;
        }

        return left && right;
    }


    public static void main(String[] args) {
        Integer[] maxPQ = new Integer[]{15, 13, 11, 7, 8, 6, 5};                // false
        Integer[] minPQ1 = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};    // true
        Integer[] minPQ2 = new Integer[]{1, 2, 3, 4, 5, 2, 7, 8, 9, 10, 11};    // false
        Integer[] minPQ3 = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 4, 11};     // false
        Integer[] minPQ4 = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 5, 10, 11};    // true

        MinPQJudge<Integer> minPQJudge = new MinPQJudge<>();
        System.out.println("test maxPQ is minPQ: " + minPQJudge.judge(maxPQ));
        System.out.println("test minPQ1 is minPQ: " + minPQJudge.judge(minPQ1));
        System.out.println("test minPQ2 is minPQ: " + minPQJudge.judge(minPQ2));
        System.out.println("test minPQ3 is minPQ: " + minPQJudge.judge(minPQ3));
        System.out.println("test minPQ4 is minPQ: " + minPQJudge.judge(minPQ4));
    }
}
