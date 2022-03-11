package Algorithm.Leetcode.pkg200;

/**
 * Created by hfc on 2022/3/3.
 *
 * 258. 各位相加
 *
 * 给定一个非负整数 num，反复将各个位上的数字相加，直到结果为一位数。返回这个结果。
 *
 * 示例 1:
 * 输入: num = 38
 * 输出: 2
 * 解释: 各位相加的过程为：
 * 38 --> 3 + 8 --> 11
 * 11 --> 1 + 1 --> 2
 * 由于 2 是一位数，所以返回 2。
 *
 * 示例 2:
 * 输入: num = 0
 * 输出: 0
 *
 * 提示：
 * 0 <= num <= 2^31 - 1
 *
 * 进阶：你可以不使用循环或者递归，在 O(1) 时间复杂度内解决这个问题吗？
 *
 */
public class Problem258 {

    /**
     * 速度 100%
     * 内存 30%
     */
    public int addDigits(int num) {
        if (num < 10) return num;

        int iter = num % 10;
        while (true) {
            if (num >= 10) {
                num /= 10;
                iter += num % 10;
            } else if (iter < 10) {
                num = iter;
                break;
            } else {
                num = iter;
                iter = num % 10;
            }
        }
        return num;
    }

    /**
     * 速度 100%
     * 内存 15%
     */
    public int addDigits1(int num) {
        int iter = num % 10;
        while (num >= 10) {
            num /= 10;
            iter += num % 10;

            if (iter >= 10) {
                iter = this.addDigits1(iter);
            }
        }
        return iter;
    }

    public static void main(String[] args) {
        Problem258 p = new Problem258();

        System.out.println(2 == p.addDigits(38));
        System.out.println(0 == p.addDigits(0));
        System.out.println(1 == p.addDigits(10));
        System.out.println(3 == p.addDigits(12));
        System.out.println(6 == p.addDigits(123));
        System.out.println(1 == p.addDigits(1234));
        System.out.println(6 == p.addDigits(12345));
        System.out.println(3 == p.addDigits(123456));
        System.out.println(1 == p.addDigits(1234567));
        System.out.println(6 == p.addDigits(247561341));
        System.out.println(1 == p.addDigits(Integer.MAX_VALUE));
    }
}
