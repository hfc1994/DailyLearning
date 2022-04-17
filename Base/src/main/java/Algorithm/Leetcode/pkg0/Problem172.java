package Algorithm.Leetcode.pkg0;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Created by hfc on 2022/3/25.
 *
 * 172. 阶乘后的零
 *
 * 给定一个整数 n ，返回 n! 结果中尾随零的数量。
 * 提示 n! = n * (n - 1) * (n - 2) * ... * 3 * 2 * 1
 *
 * 示例 1：
 * 输入：n = 3
 * 输出：0
 * 解释：3! = 6 ，不含尾随 0
 *
 * 示例 2：
 * 输入：n = 5
 * 输出：1
 * 解释：5! = 120 ，有一个尾随 0
 *
 * 示例 3：
 * 输入：n = 0
 * 输出：0
 *
 * 提示：
 * 0 <= n <= 10^4
 *
 */
public class Problem172 {

    /**
     * 速度 5%
     * 内存 5%
     */
    public int trailingZeroes1(int n) {
        if (n <= 4) return 0;

        int count = 0;
        Deque<Long> stack = new LinkedList<>();
        for (int i = 1; i <= n; i++) {
            long num = i;
            while (num % 10 == 0) {
                count++;
                num /= 10;
            }

            while (!stack.isEmpty()
                    && (stack.peek() * num) % 10 == 0) {
                num = stack.poll() * num;
                while (num % 10 == 0) {
                    count++;
                    num /= 10;
                }
            }
            if (num % 5 == 0 || num % 2 == 0) {
                stack.push(num);
            }
        }
        return count;
    }

    /**
     * 速度 5%
     * 内存 43%
     */
    public int trailingZeroes2(int n) {
        if (n <= 4) return 0;

        int count = 0;
        // idx == 0 表示 2 的个数；idx == 1 表示 5 的个数
        int[] stat = new int[2];
        for (int i = 1; i <= n; i++) {
            int num = i;
            while (num % 10 == 0) {
                count++;
                num /= 10;
            }

            while (num % 5 == 0 || num % 2 == 0) {
                if (num % 5 == 0) {
                    if (stat[0] > 0) {
                        stat[0]--;
                        count++;
                    } else {
                        stat[1]++;
                    }
                    num /= 5;
                } else {
                    if (stat[1] > 0) {
                        stat[1]--;
                        count++;
                    } else {
                        stat[0]++;
                    }
                    num /= 2;
                }
            }
        }
        return count;
    }

    /**
     * 来自题解
     */
    public int trailingZeroes3(int n) {
        int count = 0;
        for (int i = 5; i <= n; i += 5) {
            int num = i;
            while (num % 5 == 0) {
                count++;
                num /= 5;
            }
        }
        return count;
    }

    /**
     * 来自题解
     */
    public int trailingZeroes(int n) {
        int count = 0;
        while (n != 0) {
            n /= 5;
            count += n;
        }
        return count;
    }

    public static void main(String[] args) {
        Problem172 p = new Problem172();

        System.out.println(0 == p.trailingZeroes(3));
        System.out.println(1 == p.trailingZeroes(5));
        System.out.println(0 == p.trailingZeroes(0));
        System.out.println(20 == p.trailingZeroes(88));
        System.out.println(195 == p.trailingZeroes(786));
        System.out.println(2499 == p.trailingZeroes(10000));
    }

}
