package Algorithm.Leetcode.pkg200;

/**
 * Created by user-hfc on 2021/9/18.
 *
 * 292. Nim 游戏
 *
 * 你和你的朋友，两个人一起玩 Nim 游戏：
 * 桌子上有一堆石头。
 * 你们轮流进行自己的回合，你作为先手。
 * 每一回合，轮到的人拿掉 1 - 3 块石头。
 * 拿掉最后一块石头的人就是获胜者。
 * 假设你们每一步都是最优解。请编写一个函数，来判断你是否可以在给定石头数量为 n
 * 的情况下赢得游戏。如果可以赢，返回 true；否则，返回 false 。
 *
 * 示例 1：
 * 输入：n = 4
 * 输出：false
 * 解释：如果堆中有 4 块石头，那么你永远不会赢得比赛；
 *      因为无论你拿走 1 块、2 块 还是 3 块石头，最后一块石头总是会被你的朋友拿走。
 *
 * 示例 2：
 * 输入：n = 1
 * 输出：true
 *
 * 示例 3：
 * 输入：n = 2
 * 输出：true
 *
 * 提示：
 * 1 <= n <= 2^31 - 1
 *
 */
public class Problem292 {

    // 来自 leetcode 评论区

    /**
     * 周期是 4，那么 n = 4, 8, 12, 16,...的情况下会输
     * 而这些数字的二进制数值的末位两位一直都是 00，3 的二进制是 11
     */
    public boolean canWinNim_1(int n) {
        return (n & 3) != 0;
    }

    /**
     *  n = 1 胜
     *  n = 2 胜
     *  n = 3 胜
     *  n = 4 = 1 + 3 输 (我拿 1，对方拿 3) -----|
     *        = 2 + 2 输 (我拿 2，对方拿 2)    必输
     *        = 3 + 1 输 (我拿 3，对方拿 1) -----|
     *  n = 5 = 1 + 4 胜 (由 n = 4 可知，剩下 4 对面必输) ---|
     *        = 2 + 3 输                           能胜，即使用 1 + 4 方案
     *        = 3 + 2 输                                ---|
     *  n = 6 = 1 + 5 输 (由 n = 5 可知，对方用 1 + 4 就能胜) ---|
     *        = 2 + 4 胜                               能胜，即使用 2 + 4 方案
     *        = 3 + 3 输                                    ---|
     *  n = 7 = 1 + 6 输 -----|
     *        = 2 + 5 输   能胜，即使用 3 + 4 方案
     *        = 3 + 4 胜 -----|
     *
     * 由上面规律可知，每隔 4 轮都会出现一个数，这个数我不管拿掉 1、2 还是 3，
     * 剩下的数对方都能有必胜的方案
     */
    public boolean canWinNim(int n) {
        return n % 4 != 0;
    }

    public static void main(String[] args) {
        Problem292 p = new Problem292();

        System.out.println(p.canWinNim(4)); // false
        System.out.println(p.canWinNim(1)); // true
        System.out.println(p.canWinNim(2)); // true
        System.out.println(p.canWinNim(15)); // true
    }
}
