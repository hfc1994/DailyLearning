package Algorithm.Leetcode.pkg1600;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Created by hfc on 2022/1/30.
 *
 * 1765. 地图中的最高点
 *
 * 给你一个大小为 m x n 的整数矩阵 isWater ，它代表了一个由 陆地 和 水域 
 * 单元格组成的地图。
 * - 如果 isWater[i][j] == 0 ，格子 (i, j) 是一个 陆地 格子。
 * - 如果 isWater[i][j] == 1 ，格子 (i, j) 是一个 水域 格子。
 *
 * 你需要按照如下规则给每个单元格安排高度：
 * - 每个格子的高度都必须是非负的。
 * - 如果一个格子是是 水域 ，那么它的高度必须为 0 。
 * - 任意相邻的格子高度差 至多 为 1 。当两个格子在正东、南、西、北方向上相互紧挨着，
 *   就称它们为相邻的格子。（也就是说它们有一条公共边）
 *
 * 找到一种安排高度的方案，使得矩阵中的最高高度值 最大 。
 * 请你返回一个大小为 m x n 的整数矩阵 height ，其中 height[i][j] 是格子 (i, j) 
 * 的高度。如果有多种解法，请返回 任意一个 。
 *
 * 示例 1：
 * 输入：isWater = [[0,1],[0,0]]
 * 输出：[[1,0],[2,1]]
 *
 * 示例 2：
 * 输入：isWater = [[0,0,1],[1,0,0],[0,0,0]]
 * 输出：[[1,1,0],[0,1,1],[1,2,2]]
 * 解释：所有安排方案中，最高可行高度为 2 。
 * 任意安排方案中，只要最高高度为 2 且符合上述规则的，都为可行方案。
 *
 * 提示：
 * m == isWater.length
 * n == isWater[i].length
 * 1 <= m, n <= 1000
 * isWater[i][j] 要么是 0 ，要么是 1 。
 * 至少有 1 个水域格子。
 */
public class Problem1765 {

    public int[][] highestPeak(int[][] isWater) {
        int[][] heights = new int[isWater.length][isWater[0].length];
        for (int[] hh : heights) {
            Arrays.fill(hh, -1);
        }

        LinkedList<int[]> queue = new LinkedList<>();
        for (int i = 0; i < isWater.length; i++) {
            for (int j = 0; j < isWater[0].length; j++) {
                if (isWater[i][j] == 1) {
                    queue.add(new int[] {i, j});
                    heights[i][j] = 0;
                }
            }
        }

        while (!queue.isEmpty()) {
            int[] pos = queue.poll();
            int x = pos[0], y = pos[1];
            int val = heights[x][y] + 1;

            if (x > 0) this.doUpdate(x - 1, y, val, heights, queue);
            if (y > 0) this.doUpdate(x, y - 1, val, heights, queue);
            if (x < heights.length - 1) this.doUpdate(x + 1, y, val, heights, queue);
            if (y < heights[0].length - 1) this.doUpdate(x, y + 1, val, heights, queue);
        }

        return heights;
    }

    private void doUpdate(int x, int y, int val, int[][] heights, LinkedList<int[]> queue) {
        if (heights[x][y] == -1) {
            heights[x][y] = val;
            queue.add(new int[]{x, y});
        }
    }

    public static void main(String[] args) {
        Problem1765 p = new Problem1765();

        int[][] heights = p.highestPeak(new int[][]{{0, 1}, {0, 0}});
        for (int[] hh : heights) {
            System.out.println(Arrays.toString(hh));
        }

        System.out.println("----");

        heights = p.highestPeak(new int[][]{{0, 0, 1}, {1, 0, 0}, {0, 0, 0}});
        for (int[] hh : heights) {
            System.out.println(Arrays.toString(hh));
        }

        System.out.println("----");

        heights = p.highestPeak(new int[][]{{1}});
        for (int[] hh : heights) {
            System.out.println(Arrays.toString(hh));
        }

        System.out.println("----");

        heights = p.highestPeak(new int[][]{{1, 1}, {1, 1}, {1, 1}});
        for (int[] hh : heights) {
            System.out.println(Arrays.toString(hh));
        }

        System.out.println("----");

        heights = p.highestPeak(new int[][]{{1, 1}, {1, 1}, {1, 0}});
        for (int[] hh : heights) {
            System.out.println(Arrays.toString(hh));
        }

        System.out.println("----");

        heights = p.highestPeak(new int[][]{{0, 0}, {0, 0}, {1, 0}});
        for (int[] hh : heights) {
            System.out.println(Arrays.toString(hh));
        }
    }
}
