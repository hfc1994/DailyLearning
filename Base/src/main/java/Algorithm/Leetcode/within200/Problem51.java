package Algorithm.Leetcode.within200;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by hfc on 2020/9/6.
 *
 * 51. N 皇后
 *
 * n 皇后问题研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。
 * 给定一个整数 n，返回所有不同的 n 皇后问题的解决方案。
 * 每一种解法包含一个明确的 n 皇后问题的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。
 *

 * 示例：
 * 输入：4
 * 输出：[
 *  [".Q..",  // 解法 1
 *   "...Q",
 *   "Q...",
 *   "..Q."],
 *
 *  ["..Q.",  // 解法 2
 *   "Q...",
 *   "...Q",
 *   ".Q.."]
 * ]
 * 解释: 4 皇后问题存在两个不同的解法。
 *  
 *
 * 提示：
 * 皇后彼此不能相互攻击，也就是说：任何两个皇后都不能处于同一条横行、纵行或斜线上。
 *
 */
public class Problem51 {

    // 棋盘
    private int[][] board;
    // 棋盘的长宽
    private int length;

    /**
     * 当一个点位的值不为0时，说明当前点位能被某个皇后攻击
     * 当一个皇后降临到(x, y)，那么皇后能攻击的位置是(x-1, y+1)、(x, y+1)、(x+1, y+1)、（x-2, y+2）...，
     * 在对应的点加上值y+1(皇后撤销时就减去y+1)
     * 此时计算完皇后(x, y)的攻击范围，那么就开始挑选x+1行的皇后
     */
    public List<List<String>> solveNQueens(int n) {
        board = new int[n][n];
        length = n;

        List<List<String>> plans = new LinkedList<>();

        // 特殊情况
        if (n == 0) {
            plans.add(new ArrayList<>());
            return plans;
        }
        if (n == 1) {
            plans.add(Arrays.asList("Q"));
            return plans;
        }

        int[] route = new int[length];  // 每行放置皇后的位置
        for (int qx=0; qx<length; qx++) {
            route[0] = qx;  // 选择第一行的某个位置放置皇后
            flipArea(qx, 0, 1);
            searchNextLine(1, route, plans);
            flipArea(qx, 0, -1);
        }

        return plans;
    }

    private void searchNextLine(int y, int[] route, List<List<String>> plans) {
        for (int x=0; x<length ; x++) {
            if (board[x][y] == 0) {
                route[y] = x;    // 第y行选的是x位置
                if (y == length-1) {
                    // 到末尾了，计算位置
                    List<String> plan = new LinkedList<>();
                    StringBuilder sb;
                    for (int j=0; j<length; j++) {  // 行
                        sb = new StringBuilder();
                        for (int i=0; i<length; i++) {  // 列
                            if (i == route[j]) sb.append("Q");
                            else sb.append(".");
                        }
                        plan.add(sb.toString());
                    }
                    plans.add(plan);
                } else {
                    flipArea(x, y, y+1);    // 计算攻击范围
                    searchNextLine(y+1, route, plans);
                    flipArea(x, y, -y-1);   // 撤销攻击范围
                }
            }
        }
    }

    /**
     * 计算或撤销某个皇后的攻击范围
     *
     * @param x,y 皇后所在的位置，x横坐标，y纵坐标
     * @param score score为正时，说明是对某皇后的攻击范围的计算；当score为负时，说明撤销某皇后的攻击范围
     */
    private void flipArea(int x, int y, int score) {
        // i是偏移，最后一行没有计算的意义
        for (int i=1; y+i<length; i++) {
            board[x][y+i] += score;
            if (x-i >= 0) board[x-i][y+i] += score;
            if (x+i <length) board[x+i][y+i] += score;
        }
    }

    public static void main(String[] args) {
        Problem51 p = new Problem51();

        List<List<String>> plans = p.solveNQueens(1);
        System.out.println("1阶的结果总数为：" + plans.size());
        plans.forEach(plan -> {
            System.out.print("[");
            plan.forEach(str -> System.out.println("\"" + str + "\","));
            System.out.print("],");
            System.out.println();
        });

        System.out.println("======================");

        plans = p.solveNQueens(2);
        System.out.println("2阶的结果总数为：" + plans.size());
        plans.forEach(plan -> {
            System.out.print("[");
            plan.forEach(str -> System.out.println("\"" + str + "\","));
            System.out.print("],");
            System.out.println();
        });

        System.out.println("======================");

        plans = p.solveNQueens(4);
        System.out.println("4阶的结果总数为：" + plans.size());
        plans.forEach(plan -> {
            System.out.print("[");
            plan.forEach(str -> System.out.println("\"" + str + "\","));
            System.out.print("],");
            System.out.println();
        });

        System.out.println("======================");

        plans = p.solveNQueens(8);
        System.out.println("8阶的结果总数为：" + plans.size());
        plans.forEach(plan -> {
            System.out.print("[");
            plan.forEach(str -> System.out.println("\"" + str + "\","));
            System.out.print("],");
            System.out.println();
        });
    }
}
