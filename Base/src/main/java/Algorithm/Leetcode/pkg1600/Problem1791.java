package Algorithm.Leetcode.pkg1600;

/**
 * Created by hfc on 2022/3/2.
 *
 * 1791. 找出星型图的中心节点
 *
 * 有一个无向的 星型 图，由 n 个编号从 1 到 n 的节点组成。星型图有一个 中心 节点，
 * 并且恰有 n - 1 条边将中心节点与其他每个节点连接起来。
 * 给你一个二维整数数组 edges ，其中 edges[i] = [ui, vi] 表示在节点 ui 和 vi 之间存在一条边。
 * 请你找出并返回 edges 所表示星型图的中心节点。
 *
 * 示例 1：
 *          4
 *           \
 *           2
 *        /    \
 *      1       3
 * 输入：edges = [[1,2],[2,3],[4,2]]
 * 输出：2
 * 解释：如上图所示，节点 2 与其他每个节点都相连，所以节点 2 是中心节点。
 *
 * 示例 2：
 * 输入：edges = [[1,2],[5,1],[1,3],[1,4]]
 * 输出：1
 *
 * 提示：
 * 3 <= n <= 105
 * edges.length == n - 1
 * edges[i].length == 2
 * 1 <= ui, vi <= n
 * ui != vi
 * 题目数据给出的 edges 表示一个有效的星型图
 *
 */
public class Problem1791 {

    /**
     * 速度 100%
     * 内存 39%
     */
    public int findCenter(int[][] edges) {
        if (edges[0][0] == edges[1][0] || edges[0][0] == edges[1][1]) {
            return edges[0][0];
        } else {
            return edges[0][1];
        }
    }

    public static void main(String[] args) {
        Problem1791 p = new Problem1791();

        System.out.println(2 == p.findCenter(new int[][]{{1, 2}, {2, 3}, {4, 2}}));
        System.out.println(1 == p.findCenter(new int[][]{{1, 2}, {5, 1}, {1, 3}, {1, 4}}));
    }
}