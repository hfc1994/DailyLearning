package Algorithm.Leetcode.pkg400;

/**
 * Created by hfc on 2021/12/31.
 *
 * 507. 完美数
 *
 * 对于一个正整数，如果它和除了它自身以外的所有 正因子 之和相等，我们称它为 「完美数」。
 * 给定一个整数 n，如果是完美数，返回 true，否则返回 false
 *
 * 示例 1：
 * 输入：num = 28
 * 输出：true
 * 解释：28 = 1 + 2 + 4 + 7 + 14
 * 1, 2, 4, 7, 和 14 是 28 的所有正因子。
 *
 * 示例 2：
 * 输入：num = 6
 * 输出：true
 *
 * 示例 3：
 * 输入：num = 496
 * 输出：true
 *
 * 示例 4：
 * 输入：num = 8128
 * 输出：true
 *
 * 示例 5：
 * 输入：num = 2
 * 输出：false
 *
 * 提示：
 * 1 <= num <= 10^8
 *
 */
public class Problem507 {

    public boolean checkPerfectNumber1(int num) {
        if (num == 1) return false;

        int stopNum = Double.valueOf(Math.sqrt(num)).intValue();
        int acc = 1;
        int remain;
        for (int i = 2; i <= stopNum; i++) {
            if (num % i == 0) {
                remain = num / i;
                acc = acc + i;
                if (i != remain) acc = acc + remain;
            }
        }

        return num == acc;
    }

    /**
     * 内存占用稍微好一点点点
     */
    public boolean checkPerfectNumber(int num) {
        if (num == 1) return false;

        int acc = 1;
        for (int i = 2; i * i <= num; i++) {
            if (num % i == 0) {
                acc += i;
                if (i * i < num) acc += num / i;
            }
        }

        return num == acc;
    }

    public static void main(String[] args) {
        Problem507 p = new Problem507();

        System.out.println(p.checkPerfectNumber(1));    // false
        System.out.println(p.checkPerfectNumber(2));    // false
        System.out.println(p.checkPerfectNumber(28));   // true
        System.out.println(p.checkPerfectNumber(40));   // false
        System.out.println(p.checkPerfectNumber(496));  // true
        System.out.println(p.checkPerfectNumber(1000)); // false
        System.out.println(p.checkPerfectNumber(8192)); // false
    }

}
