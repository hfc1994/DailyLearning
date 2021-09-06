package Algorithm.Leetcode.pkg0;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by user-hfc on 2020/9/15.
 *
 * 37. 解数独
 *
 * 编写一个程序，通过已填充的空格来解决数独问题。
 *
 * 一个数独的解法需遵循如下规则：
 * 数字 1-9 在每一行只能出现一次。
 * 数字 1-9 在每一列只能出现一次。
 * 数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。
 * 空白格用 '.' 表示。
 *
 * Note:
 * 给定的数独序列只包含数字 1-9 和字符 '.' 。
 * 你可以假设给定的数独只有唯一解。
 * 给定数独永远是 9x9 形式的。
 */
public class Problem37 {

    char POINT = '.';
    char ZERO = '0';
    char ONE = '1';
    private int LENGTH = 9;
    private int remain = 0;

    public void solveSudoku(char[][] board) {
        // 预处理
        for (int y=0; y<LENGTH; y++) {
            for (int x=0; x<LENGTH; x++) {
                if (board[y][x] == POINT) doAnalysis(x, y, board);   // 计算可能的候选值
            }
        }

        // 计算还差几个空位，并存储其坐标
        List<Integer> axis = new LinkedList<>();
        for (int y=0; y<LENGTH; y++) {
            for (int x=0; x<LENGTH; x++) {
                if (board[y][x] == POINT) axis.add(x * 10 + y);
            }
        }
        remain = axis.size();

        while (remain != 0) {
            if (doDfs(board, axis, 0))
                break;
        }
    }

    // 返回值表示是否填充完毕
    private boolean doDfs(char[][] board, List<Integer> axis, int level) {
        if (level == axis.size()) return false;

        List<Integer> opt = new LinkedList<>();
        int x = axis.get(level) / 10, y = axis.get(level) % 10;
        int status = doAnalysis(x, y, board);
        for (int i=1; i<=LENGTH; i++) {
            if ((status >>> i-1 & 1) == 0) opt.add(i);
        }

        for (int idx=0; idx<opt.size(); idx++) {
            board[y][x] = (char) (opt.get(idx) + ZERO);
            remain--;
            if (remain ==0 || doDfs(board, axis, level+1))
                return true;
            remain++;
        }
        board[y][x] = POINT;    // 恢复原状
        return false;
    }

    // 根据小九宫格和横行竖列来计算当前xy点的可能值
    private int doAnalysis(int x, int y, char[][] board) {
        int xFrom = x/3 * 3, xTo = xFrom + 2;
        int yFrom = y/3 * 3, yTo = yFrom + 2;

        // xy点的可选结果
        int status = 0;
        // xy点不能选的数字个数
        int count = 0;
        int base;
        // 小九宫格类
        for (int i=yFrom; i<=yTo; i++) {
            for (int j=xFrom; j<=xTo; j++) {
                if (board[i][j] != POINT && (i != y || j != x)) {
                    base = 1 << (board[i][j] - ONE);
                    if ((status & base) == 0) {
                        status = status | base;
                        count++;
                    }
                }
            }
        }

        // 横向移动
        for (int i=0; i<LENGTH; i++) {
            if (board[y][i] != POINT && i != x) {
                base = 1 << (board[y][i] - ONE);
                if ((status & base) == 0) {
                    status = status | base;
                    count++;
                }
            }
        }

        // 纵向移动
        for (int j=0; j<LENGTH; j++) {
            if (board[j][x] != POINT && j != y) {
                base = 1 << (board[j][x] - ONE);
                if ((status & base) == 0) {
                    status = status | base;
                    count++;
                }
            }
        }

        if (count == 8) {
            for (int i=1; i<=LENGTH; i++) {
                if ((status >>> i-1 & 1) == 0) {
                    board[y][x] = (char) (i + ZERO);
                    break;
                }
            }
        }
        return status;
    }

    public static void main(String[] args) {
        Problem37 p = new Problem37();

        char[][] board = {
                {'5','3','.','.','7','.','.','.','.'},
                {'6','.','.','1','9','5','.','.','.'},
                {'.','9','8','.','.','.','.','6','.'},
                {'8','.','.','.','6','.','.','.','3'},
                {'4','.','.','8','.','3','.','.','1'},
                {'7','.','.','.','2','.','.','.','6'},
                {'.','6','.','.','.','.','2','8','.'},
                {'.','.','.','4','1','9','.','.','5'},
                {'.','.','.','.','8','.','.','7','9'}};
        p.solveSudoku(board);

        for (char[] cs : board) {
            for (char c : cs)
                System.out.print(String.valueOf(c) + ", ");
            System.out.println();
        }
    }
}
