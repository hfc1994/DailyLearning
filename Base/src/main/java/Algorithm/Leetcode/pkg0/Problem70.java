package Algorithm.Leetcode.pkg0;

/**
 * Created by user-hfc on 2021/9/12.
 *
 * 70. 爬楼梯
 * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
 * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
 * 注意：给定 n 是一个正整数。
 *
 * 示例 1：
 * 输入： 2
 * 输出： 2
 * 解释： 有两种方法可以爬到楼顶。
 * 1.  1 阶 + 1 阶
 * 2.  2 阶
 *
 * 示例 2：
 * 输入： 3
 * 输出： 3
 * 解释： 有三种方法可以爬到楼顶。
 * 1.  1 阶 + 1 阶 + 1 阶
 * 2.  1 阶 + 2 阶
 * 3.  2 阶 + 1 阶
 *
 */
public class Problem70 {

    // 按题意，初始输入 n != 0
    // 超时
    public int climbStairs_1(int n) {
        if (n == 0) return 1;
        if (n == 1) return 1;

        int count = climbStairs_1(n - 1);
        if (n >= 2) count += climbStairs_1(n - 2);

        return count;
    }

    // 速度和内存占用都很优异
    public int climbStairs_2(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;

        // 数组下标表示剩余index步，其值表示有value种走完index步的方式
        int[] data = new int[n + 1];
        data[0] = 1;
        data[1] = 1;
        return leftStep(n, data);
    }

    private int leftStep(int n, int[] data) {
        if (data[n] != 0) {
            return data[n];
        }

        int count = leftStep(n - 1, data);
        data[n - 1] = count;
        if (n >= 2) {
            count = leftStep(n - 2, data);
            data[n - 2] = count;
            count += data[n - 1];
        }

        return count;
    }

    // 来自leetcode题解
    public int climbStairs(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        if (n == 2) return 2;

        int l1 = 1, l2 = 2, tmp;
        for (int i = 3; i <= n; i++) {
            tmp = l1;
            l1 = l2;
            l2 = tmp + l1;
        }

        return l2;
    }

    public static void main(String[] args) {
        Problem70 p = new Problem70();

        System.out.println(p.climbStairs(2));   // 2
        System.out.println(p.climbStairs(3));   // 3
        System.out.println(p.climbStairs(4));   // 5
        System.out.println(p.climbStairs(5));   // 8
        System.out.println(p.climbStairs(12));   // 233
    }

}
