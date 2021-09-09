package Algorithm.Leetcode.pkg400;

import Algorithm.Leetcode.LeetcodeUtil;

import java.util.LinkedList;

/**
 * Created by user-hfc on 2021/9/8.
 *
 * 542. 01 矩阵
 *
 * 给定一个由 0 和 1 组成的矩阵 mat ，请输出一个大小相同的矩阵，
 * 其中每一个格子是 mat 中对应位置元素到最近的 0 的距离。
 * 两个相邻元素间的距离为 1 。
 *
 * 示例 1：
 *  0  0  0
 *  0  1  0
 *  0  0  0
 *
 * 输入：mat = [[0,0,0],[0,1,0],[0,0,0]]
 * 输出：[[0,0,0],[0,1,0],[0,0,0]]
 *
 * 示例 2：
 *  0  0  0
 *  0  1  0
 *  1  1  1
 *
 * 输入：mat = [[0,0,0],[0,1,0],[1,1,1]]
 * 输出：[[0,0,0],[0,1,0],[1,2,1]]
 *
 * 提示：
 * m == mat.length
 * n == mat[i].length
 * 1 <= m, n <= 10^4
 * 1 <= m * n <= 10^4
 * mat[i][j] is either 0 or 1.
 * mat 中至少有一个 0
 *
 */
public class Problem542 {

    public int[][] updateMatrix_1(int[][] mat) {
        LinkedList<Location> list = new LinkedList<>();
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                if (mat[i][j] == 0) {
                    list.add(new Location(i, j));
                } else {
                    mat[i][j] = -1;
                }
            }
        }

        doBfs(mat, list, 1);
        return mat;
    }

    private void doBfs(int[][] mat, LinkedList<Location> list, int distance) {
        int count = list.size();
        if (count == 0) return;

        Location loc;
        int tmpX, tmpY;
        while (count-- != 0) {
            loc = list.remove();

            tmpX = loc.x - 1;
            if (loc.x > 0 && mat[tmpX][loc.y] == -1) {
                mat[tmpX][loc.y] = distance;
                list.add(new Location(tmpX, loc.y));
            }

            tmpX = loc.x + 1;
            if (loc.x < mat.length - 1 && mat[tmpX][loc.y] == -1) {
                mat[tmpX][loc.y] = distance;
                list.add(new Location(tmpX, loc.y));
            }

            tmpY = loc.y - 1;
            if (loc.y > 0 && mat[loc.x][tmpY] == -1) {
                mat[loc.x][tmpY] = distance;
                list.add(new Location(loc.x, tmpY));
            }

            tmpY = loc.y + 1;
            if (loc.y < mat[0].length - 1 && mat[loc.x][tmpY] == -1) {
                mat[loc.x][tmpY] = distance;
                list.add(new Location(loc.x, tmpY));
            }
        }

        doBfs(mat, list, ++distance);
    }

    class Location {
        int x;
        int y;

        public Location (int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    // 速度很差
    public int[][] updateMatrix(int[][] mat) {
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                if (mat[i][j] != 0) {
                    mat[i][j] = Integer.MAX_VALUE;
                }
            }
        }

        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                if (mat[i][j] == 0) {
                    doDfs(mat, i, j, 1);
                }
            }
        }
        return mat;
    }

    private void doDfs(int[][] mat, int x, int y, int distance) {
        int tmpX = x - 1;
        if (x > 0 && mat[tmpX][y] > distance) {
            mat[tmpX][y] = distance;
            doDfs(mat, tmpX, y, distance + 1);

        }

        tmpX = x + 1;
        if (x < mat.length - 1 && mat[tmpX][y] > distance) {
            mat[tmpX][y] = distance;
            doDfs(mat, tmpX, y, distance + 1);
        }

        int tmpY = y - 1;
        if (y > 0 && mat[x][tmpY] > distance) {
            mat[x][tmpY] = distance;
            doDfs(mat, x, tmpY, distance + 1);
        }

        tmpY = y + 1;
        if (y < mat[0].length - 1 && mat[x][tmpY] > distance) {
            mat[x][tmpY] = distance;
            doDfs(mat, x, tmpY, distance + 1);
        }
    }

    public static void main(String[] args) {
        Problem542 p = new Problem542();

        int[][] mat = new int[][] { {0,0,0},{0,1,0},{0,0,0} };
        LeetcodeUtil.print2DArray(p.updateMatrix(mat)); // [[0,0,0],[0,1,0],[0,0,0]]

        mat = new int[][] { {0,0,0},{0,1,0},{1,1,1} };
        LeetcodeUtil.print2DArray(p.updateMatrix(mat)); // [[0,0,0],[0,1,0],[1,2,1]]

        mat = new int[][] { {0,1,0,1,1},{1,1,0,0,1},{0,0,0,1,0},{1,0,1,1,1},{1,0,0,0,1} };
        LeetcodeUtil.print2DArray(p.updateMatrix(mat)); // [[0,1,0,1,2],[1,1,0,0,1],[0,0,0,1,0],[1,0,1,1,1],[1,0,0,0,1]]
    }

}
