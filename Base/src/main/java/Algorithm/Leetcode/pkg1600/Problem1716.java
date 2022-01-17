package Algorithm.Leetcode.pkg1600;

/**
 * Created by hfc on 2022/1/17.
 *
 * 1716. 计算力扣银行的钱
 *
 * Hercy 想要为购买第一辆车存钱。他 每天 都往力扣银行里存钱。
 * 最开始，他在周一的时候存入 1 块钱。从周二到周日，他每天都比
 * 前一天多存入 1 块钱。在接下来每一个周一，他都会比 前一个周一 多存入 1 块钱。
 * 给你 n ，请你返回在第 n 天结束的时候他在力扣银行总共存了多少块钱。
 *
 * 示例 1：
 * 输入：n = 4
 * 输出：10
 * 解释：第 4 天后，总额为 1 + 2 + 3 + 4 = 10 。
 *
 * 示例 2：
 * 输入：n = 10
 * 输出：37
 * 解释：第 10 天后，总额为 (1 + 2 + 3 + 4 + 5 + 6 + 7) + (2 + 3 + 4) = 37 。
 * 注意到第二个星期一，Hercy 存入 2 块钱。
 *
 * 示例 3：
 * 输入：n = 20
 * 输出：96
 * 解释：第 20 天后，总额为 (1 + 2 + 3 + 4 + 5 + 6 + 7) + (2 + 3 + 4 + 5 + 6 + 7 + 8)
 *                          + (3 + 4 + 5 + 6 + 7 + 8) = 96 。
 *
 * 提示：
 * 1 <= n <= 1000
 *
 */
public class Problem1716 {

    /**
     * 速度 100%
     * 内存 92%
     */
    public int totalMoney(int n) {
        int days = n % 7;
        if (days == 0) days = 7;

        int weeks = (n - 1) / 7;

        return 28 * weeks + weeks * (weeks - 1) / 2 * 7
                + (1 + weeks + days + weeks) * days / 2;
    }

    public static void main(String[] args) {
        Problem1716 p = new Problem1716();

        System.out.println(p.totalMoney(1) == 1);
        System.out.println(p.totalMoney(2) == 3);
        System.out.println(p.totalMoney(4) == 10);
        System.out.println(p.totalMoney(10) == 37);
        System.out.println(p.totalMoney(20) == 96);
        System.out.println(p.totalMoney(888) == 59430);
    }
}
