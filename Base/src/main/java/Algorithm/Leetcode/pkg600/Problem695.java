package Algorithm.Leetcode.pkg600;

/**
 * Created by user-hfc on 2021/9/6.
 *
 * 695. 岛屿的最大面积
 *
 * 给定一个包含了一些 0 和 1 的非空二维数组 grid 。
 * 一个 岛屿 是由一些相邻的 1 (代表土地) 构成的组合，这里的「相邻」要求两个 1
 * 必须在水平或者竖直方向上相邻。你可以假设 grid 的四个边缘都被 0（代表水）包围着。
 * 找到给定的二维数组中最大的岛屿面积。(如果没有岛屿，则返回面积为 0 。)
 *
 * 示例 1:
 * [[0,0,1,0,0,0,0,1,0,0,0,0,0},
 *  {0,0,0,0,0,0,0,1,1,1,0,0,0},
 *  {0,1,1,0,1,0,0,0,0,0,0,0,0},
 *  {0,1,0,0,1,1,0,0,1,0,1,0,0},
 *  {0,1,0,0,1,1,0,0,1,1,1,0,0},
 *  {0,0,0,0,0,0,0,0,0,0,1,0,0},
 *  {0,0,0,0,0,0,0,1,1,1,0,0,0},
 *  {0,0,0,0,0,0,0,1,1,0,0,0,0]]
 *
 * 对于上面这个给定矩阵应返回 6。注意答案不应该是 11 ，因为岛屿只能包含水平或垂直的四个方向的 1 。
 *
 * 示例 2:
 * [[0,0,0,0,0,0,0,0]]
 *
 * 对于上面这个给定的矩阵, 返回 0。
 *
 * 注意: 给定的矩阵grid 的长度和宽度都不超过 50。
 *
 */
public class Problem695 {

    public int maxAreaOfIsland_1(int[][] grid) {
        boolean[][] mark = new boolean[grid.length][grid[0].length];

        int maxCount = 0, count;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (mark[i][j]) continue;

                count = doDfs_1(grid, mark, i, j, 0);

                if (count > maxCount) maxCount = count;
            }
        }

        return maxCount;
    }

    private int doDfs_1(int[][] grid, boolean[][] mark, int i, int j, int count) {
        if (grid[i][j] == 0 || mark[i][j]) {
            mark[i][j] = true;
            return count;
        }

        count++;
        mark[i][j] = true;
        if (i > 0) count = doDfs_1(grid, mark, i - 1, j, count);

        if (i < grid.length - 1) count = doDfs_1(grid, mark, i + 1, j, count);

        if (j > 0) count = doDfs_1(grid, mark, i, j - 1, count);

        if (j < grid[0].length - 1) count = doDfs_1(grid, mark, i, j + 1, count);

        return count;
    }

    // 参考自leetcode评论区，速度会更快一些
    public int maxAreaOfIsland(int[][] grid) {
        int maxCount = 0, count;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                count = doDfs(grid, i, j, 0);
                if (count > maxCount) maxCount = count;
            }
        }

        return maxCount;
    }

    private int doDfs(int[][] grid, int i, int j, int count) {
        if (grid[i][j] == 0) return count;

        count++;
        grid[i][j] = 0;
        if (i > 0) count = doDfs(grid, i - 1, j, count);

        if (i < grid.length - 1) count = doDfs(grid, i + 1, j, count);

        if (j > 0) count = doDfs(grid, i, j - 1, count);

        if (j < grid[0].length - 1) count = doDfs(grid, i, j + 1, count);

        return count;
    }

    public static void main(String[] args) {
        Problem695 p = new Problem695();

        int[][] grid = new int[][] {
                {0,0,1,0,0,0,0,1,0,0,0,0,0},
                {0,0,0,0,0,0,0,1,1,1,0,0,0},
                {0,1,1,0,1,0,0,0,0,0,0,0,0},
                {0,1,0,0,1,1,0,0,1,0,1,0,0},
                {0,1,0,0,1,1,0,0,1,1,1,0,0},
                {0,0,0,0,0,0,0,0,0,0,1,0,0},
                {0,0,0,0,0,0,0,1,1,1,0,0,0},
                {0,0,0,0,0,0,0,1,1,0,0,0,0}};
        System.out.println(p.maxAreaOfIsland(grid));    // 6

        grid = new int[][] { {0,0,0,0,0,0,0,0} };
        System.out.println(p.maxAreaOfIsland(grid));    // 0
    }

}
