package Algorithm.Leetcode.pkg2000;

/**
 * Created by hfc on 2022/3/11.
 *
 * 2049. 统计最高分的节点数目
 *
 * 给你一棵根节点为 0 的 二叉树 ，它总共有 n 个节点，节点编号为 0 到 n - 1 。
 * 同时给你一个下标从 0 开始的整数数组 parents 表示这棵树，其中 parents[i] 
 * 是节点 i 的父节点。由于节点 0 是根，所以 parents[0] == -1 。
 *一个子树的 大小 为这个子树内节点的数目。每个节点都有一个与之关联的 分数 。
 * 求出某个节点分数的方法是，将这个节点和与它相连的边全部 删除 ，剩余部分是
 * 若干个 非空 子树，这个节点的 分数 为所有这些子树 大小的乘积 。
 * 请你返回有 最高得分 节点的 数目 。
 *
 * 示例 1:
 *          0
 *        /   \
 *       2     4
 *     /  \
 *    3    1
 * 输入：parents = [-1,2,0,2,0]
 * 输出：3
 * 解释：
 * - 节点 0 的分数为：3 * 1 = 3
 * - 节点 1 的分数为：4 = 4
 * - 节点 2 的分数为：1 * 1 * 2 = 2
 * - 节点 3 的分数为：4 = 4
 * - 节点 4 的分数为：4 = 4
 * 最高得分为 4 ，有三个节点得分为 4 （分别是节点 1，3 和 4 ）。
 *
 * 示例 2：
 * 输入：parents = [-1,2,0]
 * 输出：2
 * 解释：
 * - 节点 0 的分数为：2 = 2
 * - 节点 1 的分数为：2 = 2
 * - 节点 2 的分数为：1 * 1 = 1
 * 最高分数为 2 ，有两个节点分数为 2 （分别为节点 0 和 1 ）。
 *
 * 提示：
 * n == parents.length
 * 2 <= n <= 10^5
 * parents[0] == -1
 * 对于 i != 0 ，有 0 <= parents[i] <= n - 1
 * parents 表示一棵二叉树。
 *
 */
public class Problem2049 {

    /**
     * 超时
     */
    public int countHighestScoreNodes1(int[] parents) {
        int[][] stat = new int[parents.length][2];
        // 统计 i 位的两个子节点是哪两个，0 位没有父节点
        for (int i = 1; i < parents.length; i++) {
            int idx = parents[i];
            if (stat[idx][0] == 0) {
                stat[idx][0] = i;
            } else {
                stat[idx][1] = i;
            }
        }

        long maxScore = -1;
        int count = 0;
        // 去掉一个节点最多裂成 3 份
        for (int i = 0; i < parents.length; i++) {
            long score = 1;
            int left = parents.length;
            for (int val : stat[i]) {
                if (val != 0) {
                    int tmp = this.calcCount(stat, val, i);
                    left -= tmp;
                    score *= tmp;
                }
            }
            if (i != 0) score *= (left - 1);

            if (score > maxScore) {
                maxScore = score;
                count = 1;
            } else if (score == maxScore) {
                count++;
            }
        }
        return count;
    }

    private int calcCount(int[][] stat, int start, int remove) {
        int count = 0;
        for (int child : stat[start]) {
            if (child != 0 && child != remove) {
                count += this.calcCount(stat, child, remove);
            }
        }
        return count + 1;
    }

    /**
     * 速度 69%
     * 内存 99%
     */
    public int countHighestScoreNodes(int[] parents) {
        int[][] stat = new int[parents.length][2];
        // 统计 i 位的两个子节点是哪两个，0 位没有父节点
        for (int i = 1; i < parents.length; i++) {
            int idx = parents[i];
            if (stat[idx][0] == 0) {
                stat[idx][0] = i;
            } else {
                stat[idx][1] = i;
            }
        }

        int[][] cCnt = new int[parents.length][2];
        this.calcChildCnt(stat, 0, cCnt);

        long maxScore = -1;
        int count = 0;
        for (int i = 0; i < parents.length; i++) {
            long score = 1;
            if (cCnt[i][0] != 0) score *= cCnt[i][0];
            if (cCnt[i][1] != 0) score *= cCnt[i][1];
            if (i != 0) score *= (parents.length - cCnt[i][0] - cCnt[i][1] - 1);

            if (score > maxScore) {
                maxScore = score;
                count = 1;
            } else if (score == maxScore) {
                count++;
            }
        }
        return count;
    }

    private int calcChildCnt(int[][] stat, int begin, int[][] cCnt) {
        int count = 1;
        int[] children = stat[begin];
        for (int i = 0; i < children.length; i++) {
            if (children[i] != 0) {
                int tmp = this.calcChildCnt(stat, children[i], cCnt);
                cCnt[begin][i] = tmp;
                count += tmp;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        Problem2049 p = new Problem2049();

        System.out.println(3 == p.countHighestScoreNodes(new int[] {-1, 2, 0, 2, 0}));
        System.out.println(2 == p.countHighestScoreNodes(new int[] {-1, 2, 0}));
        System.out.println(2 == p.countHighestScoreNodes(new int[] {-1, 0}));
        System.out.println(1 == p.countHighestScoreNodes(new int[] {-1, 9, 4, 0, 8, 0, 2, 3, 3, 5, 1, 7}));
    }
}
