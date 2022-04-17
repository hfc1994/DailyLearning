package Algorithm.Leetcode.pkg600;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by hfc on 2022/3/28.
 *
 * 693. 交替位二进制数
 *
 * 给定一个正整数，检查它的二进制表示是否总是 0、1 交替出现：换句话说，就是二进制表示
 * 中相邻两位的数字永不相同。
 *
 * 示例 1：
 * 输入：n = 5
 * 输出：true
 * 解释：5 的二进制表示是：101
 *
 * 示例 2：
 * 输入：n = 7
 * 输出：false
 * 解释：7 的二进制表示是：111.
 *
 * 示例 3：
 * 输入：n = 11
 * 输出：false
 * 解释：11 的二进制表示是：1011.
 *
 * 提示：
 * 1 <= n <= 2^31 - 1
 *
 */
public class Problem693 {

    private static Set<Integer> set;

    /**
     * 速度 100%
     * 内存 8%
     */
    public boolean hasAlternatingBits(int n) {
        if (set == null) {
            set = new HashSet<>(32);
            int base = 0;
            for (int i = 0; i < 31; i++) {
                if (i == 0) {
                    base = 1;
                } else if (i % 2 != 0) {
                    base *= 2;
                } else {
                    base = base * 2 + 1;
                }
                set.add(base);
            }
        }

        return set.contains(n);
    }

    /**
     * 来自题解
     */
    public boolean hasAlternatingBits1(int n) {
        int a = n ^ (n >> 1);   // 交替的值必然全为1
        return (a & (a + 1)) == 0;
    }

    public static void main(String[] args) {
        Problem693 p = new Problem693();

        System.out.println(p.hasAlternatingBits(5));
        System.out.println(!p.hasAlternatingBits(7));
        System.out.println(!p.hasAlternatingBits(11));
    }
}
