package Algorithm.Leetcode;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by hfc on 2020/9/20.
 *
 * 685. 冗余连接 II
 *
 * 在本问题中，有根树指满足以下条件的有向图。该树只有一个根节点，所有其他节点都是该根节点的后继。
 * 每一个节点只有一个父节点，除了根节点没有父节点。
 * 输入一个有向图，该图由一个有着N个节点 (节点值不重复1, 2, ..., N) 的树及一条附加的边构成。
 * 附加的边的两个顶点包含在1到N中间，这条附加的边不属于树中已存在的边。
 * 结果图是一个以边组成的二维数组。 每一个边 的元素是一对 [u, v]，用以表示有向图中连接顶点 u 和顶点 v 的边，
 * 其中 u 是 v 的一个父节点。
 * 返回一条能删除的边，使得剩下的图是有N个节点的有根树。若有多个答案，返回最后出现在给定二维数组的答案。
 *
 * 示例 1:
 * 输入: [[1,2], [1,3], [2,3]]
 * 输出: [2,3]
 *
 * 解释: 给定的有向图如下:
 *   1
 *  / \
 * v   v
 * 2-->3
 *
 * 示例 2:
 * 输入: [[1,2], [2,3], [3,4], [4,1], [1,5]]
 * 输出: [4,1]
 *
 * 解释: 给定的有向图如下:
 * 5 <- 1 -> 2
 *      ^    |
 *      |    v
 *      4 <- 3
 *
 * 注意:
 * 二维数组大小的在3到1000范围内。
 * 二维数组中的每个整数在1到N之间，其中 N 是二维数组的大小。
 *
 * 思路：
 * 按照题意，多余的一条边会产生3种异常的有向图
 * 1、有1个点有2个父点，即入度为2。如下图，此时只要删去构成2入度情况的最后出现的边即可
 *      3 -> 1 <- 2
 * 2、形成了一个环。如示例2的图，此时只要删去构成环的最后出现的边即可
 * 3、形成环的同时也出现了入度为2的点。如下图，此时需要删除环和2入度的公共边（或是最晚出现的公共边）
 *      1 -> 2
 *      ^    |
 *      |    v
 *      4 <- 3 <- 5
 *
 */
public class Problem685 {

    public int[] findRedundantDirectedConnection(int[][] edges) {
        // 使用连通图来存储当前点的父点index（UnionFind也被称为并查集）
        int[] uf = new int[edges.length+1];

        // 按照题意，最多只会同时出现一个环 + 一个双入度情况，最多有3条可选边
        List<int[]> rets = new LinkedList<>();
        int[] ret = new int[0];

        int val0, val1;
        for (int[] edge : edges) {
            val0 = getUfRoot(uf, edge[0]);
            val1 = getUfRoot(uf, edge[1]);

            // 新边的根父点相同则是在一个环上
            if (val0 == val1) {
                int[] edgeTmp, retTmp = null;
                // 判断是否提前出现入度为2的情况
                for (int i=0; i<rets.size(); i++) {
                    edgeTmp = rets.get(i);
                    // 能匹配到，则说明这个边既引起2入度，也引起环问题
                    if (val0 == getUfRoot(uf, edgeTmp[0])
                            && val0 == getUfRoot(uf, edgeTmp[1]))
                        retTmp = edgeTmp;
                }

                // 如果还没出现2入度，那么优先选择构成环的最后一条边
                rets.add(retTmp == null ? edge : retTmp);
            } else if (uf[edge[1]] != 0) {  // 已经有一个入度了，那么此时产生2入度问题
                // 等于0说明还没出现环问题
                if (rets.size() == 0) {
                    rets.add(new int[]{uf[edge[1]], edge[1]});
                    rets.add(edge);
                } else {
                    val0 = getUfRoot(uf, rets.get(rets.size() - 1)[0]);
                    // 从两个边里找到能解决环问题的
                    // 按照题意，同时出现时，必有一个公共边
                    if (val0 == getUfRoot(uf, edge[0])) rets.add(edge);
                    else rets.add(new int[]{uf[edge[1]], edge[1]});
                }
            } else {
                // 还没有入度的情况下增加一个入度
                uf[edge[1]] = edge[0];
            }
        }

        if (rets.size() > 0)
            ret = rets.get(rets.size() -1);

        return ret;
    }

    // 获取当前点的根父点的index
    private int getUfRoot(int[] uf, int idx) {
        while (uf[idx] != 0)
            idx = uf[idx];
        return idx;
    }

    public static void main(String[] args) {
        Problem685 p = new Problem685();

        int[][] edges = {{1, 2}, {1, 3}, {2, 3}};
        int[] ret;
        ret = p.findRedundantDirectedConnection(edges);
        System.out.println(ret[0] + " -> " + ret[1]);   // 2 -> 3

        edges = new int[][]{{1, 2}, {2, 3}, {3, 4}, {4, 1}, {1, 5}};
        ret = p.findRedundantDirectedConnection(edges);
        System.out.println(ret[0] + " -> " + ret[1]);   // 4 -> 1

        edges = new int[][]{{1, 2}, {2, 1}};
        ret = p.findRedundantDirectedConnection(edges);
        System.out.println(ret[0] + " -> " + ret[1]);   // 2 -> 1

        edges = new int[][]{{2, 1}, {3, 1}, {4, 2}, {1, 4}};
        ret = p.findRedundantDirectedConnection(edges);
        System.out.println(ret[0] + " -> " + ret[1]);   // 2 -> 1

        edges = new int[][]{{3, 4}, {4, 1}, {1, 2}, {2, 3}, {5, 1}};
        ret = p.findRedundantDirectedConnection(edges);
        System.out.println(ret[0] + " -> " + ret[1]);   // 4 -> 1
    }

}
