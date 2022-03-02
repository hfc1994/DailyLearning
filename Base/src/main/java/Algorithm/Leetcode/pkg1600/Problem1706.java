package Algorithm.Leetcode.pkg1600;

import Algorithm.Leetcode.LeetcodeUtil;

/**
 * Created by hfc on 2022/2/24.
 *
 * 1706. 球会落何处
 *
 * 用一个大小为 m x n 的二维网格 grid 表示一个箱子。你有 n 颗球。
 * 箱子的顶部和底部都是开着的。
 * 箱子中的每个单元格都有一个对角线挡板，跨过单元格的两个角，可以将球导向左侧或者右侧。
 *
 * 将球导向右侧的挡板跨过左上角和右下角，在网格中用 1 表示。
 * 将球导向左侧的挡板跨过右上角和左下角，在网格中用 -1 表示。
 * 在箱子每一列的顶端各放一颗球。每颗球都可能卡在箱子里或从底部掉出来。
 * 如果球恰好卡在两块挡板之间的 "V" 形图案，或者被一块挡导向到箱子的任意一侧边上，就会卡住。
 * 返回一个大小为 n 的数组 answer ，其中 answer[i] 是球放在顶部的第 i 列后从底部掉出来的那
 * 一列对应的下标，如果球卡在盒子里，则返回 -1 。
 *
 * 示例 1：
 * 输入：grid = [[1,1,1,-1,-1],[1,1,1,-1,-1],[-1,-1,-1,1,1],[1,1,1,1,-1],[-1,-1,-1,-1,-1]]
 * 输出：[1,-1,-1,-1,-1]
 * 解释：示例如图：
 * b0 球开始放在第 0 列上，最终从箱子底部第 1 列掉出。
 * b1 球开始放在第 1 列上，会卡在第 2、3 列和第 1 行之间的 "V" 形里。
 * b2 球开始放在第 2 列上，会卡在第 2、3 列和第 0 行之间的 "V" 形里。
 * b3 球开始放在第 3 列上，会卡在第 2、3 列和第 0 行之间的 "V" 形里。
 * b4 球开始放在第 4 列上，会卡在第 2、3 列和第 1 行之间的 "V" 形里。
 *
 * 示例 2：
 * 输入：grid = [[-1]]
 * 输出：[-1]
 * 解释：球被卡在箱子左侧边上。
 *
 * 示例 3：
 * 输入：grid = [[1,1,1,1,1,1],[-1,-1,-1,-1,-1,-1],[1,1,1,1,1,1],[-1,-1,-1,-1,-1,-1]]
 * 输出：[0,1,2,3,4,-1] 
 *
 * 提示：
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 100
 * grid[i][j] 为 1 或 -1
 *
 */
public class Problem1706 {

    /**
     * 速度 73%
     * 内存 22%
     */
    public int[] findBall(int[][] grid) {
        int[] dstPath = new int[grid[0].length];
        for (int i=0; i<grid[0].length; i++) {
            dstPath[i] = this.ballToGo(-1, i, 0, i, grid);
        }

        return dstPath;
    }

    private int ballToGo(int fromX, int fromY, int toX, int toY, int[][] grid) {
        if (grid[toX][toY] == 1) {
            if (fromY == toY) { // 从上往下
                fromX = toX;
                if (++toY >= grid[0].length) return -1;
            } else if (fromY < toY) {   // 从左往右
                fromY = toY;
                if (++toX >= grid.length) return toY;
            } else {    // 从右往左
                return -1;
            }
        } else {
            if (fromY == toY) { // 从上往下
                fromX = toX;
                if (--toY < 0) return -1;
            } else if (fromY < toY) {   // 从左往右
                return -1;
            } else {    // 从右往左
                fromY = toY;
                if (++toX >= grid.length) return toY;
            }
        }
        return this.ballToGo(fromX, fromY, toX, toY, grid);
    }

    public static void main(String[] args) {
        Problem1706 p = new Problem1706();

        int[][] grid = new int[][] {
                {1,1,1,-1,-1}, {1,1,1,-1,-1},
                {-1,-1,-1,1,1}, {1,1,1,1,-1},
                {-1,-1,-1,-1,-1}
        };
        int[] dstPath = p.findBall(grid);
        System.out.println(LeetcodeUtil.equalsOfArray(dstPath, new int[]{1, -1, -1, -1, -1}));

        grid = new int[][] {
                {-1}
        };
        dstPath = p.findBall(grid);
        System.out.println(LeetcodeUtil.equalsOfArray(dstPath, new int[]{-1}));

        grid = new int[][] {
                {1,1,1,1,1,1},{-1,-1,-1,-1,-1,-1},
                {1,1,1,1,1,1},{-1,-1,-1,-1,-1,-1}
        };
        dstPath = p.findBall(grid);
        System.out.println(LeetcodeUtil.equalsOfArray(dstPath, new int[]{0, 1, 2, 3, 4, -1}));
    }

}
