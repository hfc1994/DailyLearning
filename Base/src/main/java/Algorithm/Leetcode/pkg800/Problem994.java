package Algorithm.Leetcode.pkg800;

import java.util.LinkedList;

/**
 * Created by user-hfc on 2021/9/9.
 *
 * 994. 腐烂的橘子
 *
 * 在给定的网格中，每个单元格可以有以下三个值之一：
 * 值 0 代表空单元格；
 * 值 1 代表新鲜橘子；
 * 值 2 代表腐烂的橘子。
 * 每分钟，任何与腐烂的橘子（在 4 个正方向上）相邻的新鲜橘子都会腐烂。
 * 返回直到单元格中没有新鲜橘子为止所必须经过的最小分钟数。如果不可能，返回 -1。
 *
 * 示例 1：
 *
 *   2  1  1      2  2  1      2  2  2      2  2  2      2  2  2
 *   1  1  0  ->  2  1  0  ->  2  2  0  ->  2  2  0  ->  2  2  0
 *   0  1  1      0  1  1      0  1  1      0  2  1      0  2  2
 *
 * 输入：[[2,1,1],[1,1,0],[0,1,1]]
 * 输出：4
 *
 * 示例 2：
 * 输入：[[2,1,1],[0,1,1],[1,0,1]]
 * 输出：-1
 * 解释：左下角的橘子（第 2 行， 第 0 列）永远不会腐烂，因为腐烂只会发生在 4 个正向上。
 *
 * 示例 3：
 * 输入：[[0,2]]
 * 输出：0
 * 解释：因为 0 分钟时已经没有新鲜橘子了，所以答案就是 0 。
 *
 * 提示：
 * 1 <= grid.length <= 10
 * 1 <= grid[0].length <= 10
 * grid[i][j] 仅为 0、1 或 2
 *
 */
public class Problem994 {

    public int orangesRotting(int[][] grid) {
        LinkedList<Location> list = new LinkedList<>();
        int orange = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 2) {
                    list.add(new Location(i, j));
                } else if (grid[i][j] == 1) {
                    orange++;
                }
            }
        }

        if (orange == 0) return 0;
        if (list.size() == 0) return -1;

        int minute = 0;
        int size;
        Location loc;
        while (true) {
            minute++;
            size = list.size();
            while (size-- != 0) {
                loc = list.remove();

                if (loc.x > 0)
                    orange = checkAndExchage(grid, loc.x - 1, loc.y, list, orange);

                if (loc.x < grid.length - 1)
                    orange = checkAndExchage(grid, loc.x + 1, loc.y, list, orange);

                if (loc.y > 0)
                    orange = checkAndExchage(grid, loc.x, loc.y - 1, list, orange);

                if (loc.y < grid[0].length - 1)
                    orange = checkAndExchage(grid, loc.x, loc.y + 1, list, orange);
            }

            if (list.size() == 0 || orange == 0)
                break;
        }

        if (orange == 0) return minute;
        else return -1;
    }

    // 提取成方法反而增加了内存占用
    private int checkAndExchage(int[][] grid, int x, int y, LinkedList<Location> list, int orange) {
        if (grid[x][y] == 1) {
            grid[x][y] = 2;
            list.add(new Location(x, y));
            return --orange;
        }
        return orange;
    }

    class Location {
        int x;
        int y;

        public Location (int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) {
        Problem994 p = new Problem994();

        int[][] grid = new int[][] { {2,1,1},{1,1,0},{0,1,1} };
        System.out.println(p.orangesRotting(grid)); // 4

        grid = new int[][] { {2,1,1},{0,1,1},{1,0,1} };
        System.out.println(p.orangesRotting(grid)); // -1

        grid = new int[][] { {0,2} };
        System.out.println(p.orangesRotting(grid)); // 0

        grid = new int[][] { {0} };
        System.out.println(p.orangesRotting(grid)); // 0
    }

}
