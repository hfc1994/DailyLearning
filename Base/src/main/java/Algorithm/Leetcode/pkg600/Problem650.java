package Algorithm.Leetcode.pkg600;

/**
 * Created by user-hfc on 2021/9/19.
 *
 * 650. 只有两个键的键盘
 *
 * 最初记事本上只有一个字符 'A' 。你每次可以对这个记事本进行两种操作：
 * Copy All（复制全部）：复制这个记事本中的所有字符（不允许仅复制部分字符）。
 * Paste（粘贴）：粘贴 上一次 复制的字符。
 * 给你一个数字 n ，你需要使用最少的操作次数，在记事本上输出 恰好 n 个 'A' 。
 * 返回能够打印出 n 个 'A' 的最少操作次数。
 *
 * 示例 1：
 * 输入：3
 * 输出：3
 * 解释：
 * 最初, 只有一个字符 'A'。
 * 第 1 步, 使用 Copy All 操作。
 * 第 2 步, 使用 Paste 操作来获得 'AA'。
 * 第 3 步, 使用 Paste 操作来获得 'AAA'。
 *
 * 示例 2：
 * 输入：n = 1
 * 输出：0
 *
 * 提示：
 * 1 <= n <= 1000
 *
 */
public class Problem650 {

    /**
     * 来自 leetcode 题解，分解质因数法
     * https://leetcode-cn.com/problems/2-keys-keyboard/solution/gong-shui-san-xie-yi-ti-san-jie-dong-tai-f035/
     *
     * 依旧不是很懂
     */
    public int minSteps_2(int n) {
        int ans = 0;
        for (int i = 2; i * i <= n; i++) {
            while (n % i == 0) {
                ans += i;
                n /= i;
            }
        }
        if (n != 1) ans += n;
        return ans;
    }

    /**
     * 来自 leetcode 评论区
     * 比如 4 能想到 f(4) = 2 + f(2)
     * 5 怎么都不能进行类似 4 的翻倍操作所以需要每次复制 1 共 5 次
     * 6 那你肯定能想到由 f(6) = 2 + f(3) 数字 3 复制 1 次粘贴 1 次共 2 次
     * 7 同 5 一样没法翻倍
     * f(8)=2+f(4)
     * f(9)=3+f(3)
     * f(10)=2+f(5)
     * f(12)=2+f(6)
     *
     * 速度和内存占用都好
     */
    public int minSteps_1(int n) {
        if (n == 1) return 0;

        for (int i = 2; i <= n; i++) {
            if (n % i == 0) {
                return i + minSteps_1(n / i);
            }
        }
        return n;
    }

    /**
     * 画树状图分析规律
     *
     * 速度较慢，内存占用较好
     */
    public int minSteps(int n) {
        if (n == 1) return 0;
        return dfs(n, 1, 1, 1);
    }

    private int dfs(int n, int base, int acc, int depth) {
        if (n == base) return depth;
        if (n < base) return -1;

        int d1 = -1, d2 = -1;
        base = base + acc;
        if (n % acc == 0) d1 = dfs(n, base, acc, depth + 1);
        if (n % base == 0) d2 = dfs(n, base, base, depth + 2);

        if (d1 == -1) return d2;
        else if (d2 == -1) return d1;
        else if (d1 <= d2) return d1;
        return d2;
    }

    public static void main(String[] args) {
        Problem650 p = new Problem650();

        System.out.println(p.minSteps(889));  // 134
        System.out.println(p.minSteps(12));  // 7
        System.out.println(p.minSteps(6));  // 5
        System.out.println(p.minSteps(9));  // 6
        System.out.println(p.minSteps(4));  // 4
        System.out.println(p.minSteps(3));  // 3
        System.out.println(p.minSteps(2));  // 2
        System.out.println(p.minSteps(1));  // 0
    }
}
