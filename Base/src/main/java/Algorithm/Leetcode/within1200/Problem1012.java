package Algorithm.Leetcode.within1200;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user-hfc on 2021/8/24.
 *
 * 1012. 至少有 1 位重复的数字
 *
 * 给定正整数 N，返回小于等于 N 且具有至少 1 位重复数字的正整数的个数。
 *
 * 示例 1：
 * 输入：20
 * 输出：1
 * 解释：具有至少 1 位重复数字的正数（<= 20）只有 11 。
 *
 * 示例 2：
 * 输入：100
 * 输出：10
 * 解释：具有至少 1 位重复数字的正数（<= 100）有 11，22，33，44，55，66，77，88，99 和 100 。
 *
 * 示例 3：
 * 输入：1000
 * 输出：262
 *  
 * 提示：
 * 1 <= N <= 10^9
 *
 */
public class Problem1012 {

    /**
     * 20、100、1000、1_000_0、1_000_000_000 一组 26500ms 左右
     * 1_000_000_000 的情况下运行时间很长
     */
    public int numDupDigitsAtMostN_1(int n) {
        int count = 0;
        int[] flags = new int[10];  // 0-9数字的标志位

        boolean change = false;
        int num, mod;
        for (int i = 11; i <= n; i++) {
            num = i;

            if (change) {
                this.initFlag(flags);
                change = false;
            }

            while (num != 0) {
                mod = num % 10;

                if (flags[mod] == 0) {
                    flags[mod] = 1;
                    change = true;
                } else {
                    count++;
                    break;
                }

                num = num / 10;
            }
        }

        return count;
    }

    private void initFlag(int[] flags) {
        for (int i = 0; i < flags.length; i++) {
            flags[i] = 0;
        }
    }

    /**
     * 20、100、1000、1_000_0、1_000_000_000 一组 23500ms 左右
     * 1_000_000_000 的情况下运行时间很长
     */
    public int numDupDigitsAtMostN_2(int n) {
        int count = 0;
        int flag;

        int num, mod;
        for (int i = 11; i <= n; i++) {
            num = i;
            flag = 0;

            while (num != 0) {
                mod = num % 10;

                if ((flag & (1 << mod)) == 0) {
                    flag = flag | (1 << mod);
                } else {
                    count++;
                    break;
                }

                num = num / 10;
            }
        }

        return count;
    }

    /**
     * 参考自：https://leetcode.com/problems/numbers-with-repeated-digits/discuss/256725/JavaPython-Count-the-Number-Without-Repeated-Digit
     *
     * 根据 leetcode 题解可知，本体可以转换成小于等于 N 以内没有重复数字
     * 的数值，求的 count 之后，本体的结果就是 N - count
     * 因此本题实际就是个排列组合题
     */
    public int numDupDigitsAtMostN(int n) {
        List<Integer> list = new ArrayList<>();
        // 后面的双层循环第二层，j < list.get(i)，所以会导致 n 本身没被比较
        for (int temp = n + 1; temp != 0; temp /= 10) {
            list.add(0, temp % 10);
        }

        int size = list.size();

        if (size <= 1) return 0;

        int count = 0;
        // 假设 n = 3421，先计算 xx 和 xxx（也就是 [0, 1000) 以内） 里有多少不重复的
        for (int i = 1; i < size; i++) {
            // 最高位不能为 0，所以首位只有9种
            count += 9 * A(9, i - 1);
        }

        // 计算 xxxx（也就是 [1000, 3421]） 中的不重复数
        // 实际逻辑就是先计算 1xxx 里符合的个数，再 2xxx，然后是 31xx、32xx、33xx、341x、3420和3421
        boolean[] used = new boolean[10];
        // i 是第几位
        for (int i = 0; i < size; i++) {
            // j 是当前位候选的数字
            for (int j = 0; j < list.get(i); j++) {
                // 第一位即最高位不能为 0
                if (i == 0 && j == 0) continue;
                if (!used[j]) {
                    // 最高位通过 i 循环累加，所以从9开始
                    count += A(9 - i, size - i - 1);
                }
            }

            if (used[list.get(i)])
                break;
            else
                used[list.get(i)] = true;
        }

        return n - count;
    }

    /**
     * 排列组合算法，从 m 个不同元素中取出 n 个有多少种不同的组合
     */
    public int A(int m, int n) {
        return n == 0 ? 1 : m * A(m -1, n -1);
    }

    public static void main(String[] args) {
        Problem1012 p = new Problem1012();

        long begin = System.currentTimeMillis();
        System.out.println(p.numDupDigitsAtMostN(20) == 1);
        System.out.println(p.numDupDigitsAtMostN(100) == 10);
        System.out.println(p.numDupDigitsAtMostN(110) == 12);
        System.out.println(p.numDupDigitsAtMostN(1000) == 262);
        System.out.println(p.numDupDigitsAtMostN(1_000_0) == 4726);
        System.out.println(p.numDupDigitsAtMostN(1_000_000_000) == 994388230);
        System.out.println(System.currentTimeMillis() - begin);
    }

}
