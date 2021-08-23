package Algorithm.Leetcode.within200;

/**
 * Created by hfc on 2020/9/13.
 *
 * 79. 单词搜索
 *
 * 给定一个二维网格和一个单词，找出该单词是否存在于网格中。
 * 单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。
 * 同一个单元格内的字母不允许被重复使用。
 *
 * 示例:
 * board =
 * [
 *   ['A','B','C','E'],
 *   ['S','F','C','S'],
 *   ['A','D','E','E']
 * ]
 *
 * 给定 word = "ABCCED", 返回 true
 * 给定 word = "SEE", 返回 true
 * 给定 word = "ABCB", 返回 false
 *  
 *
 * 提示：
 * board 和 word 中只包含大写和小写英文字母。
 * 1 <= board.length <= 200
 * 1 <= board[i].length <= 200
 * 1 <= word.length <= 10^3
 *
 */
public class Problem79 {

    private int xAxis;
    private int yAxis;
    private char[][] board;
    private boolean[][] marked;

    /**
     * 挨个点的上下左右都依次作比较
     */
    public boolean exist(char[][] board, String word) {

        if (word.length() == 0) return false;
        xAxis = board.length;
        yAxis = board[0].length;

        marked = new boolean[board.length][board[0].length];
        this.board = board;

        char[] cs;
        char head = word.charAt(0);
        for (int i=0; i<board.length; i++) {
            cs = board[i];
            for (int j=0; j<cs.length; j++) {
                if (cs[j] == head) {
                    if (word.length() == 1) return true;
                    marked[i][j] = true;
                    if (dfs(i, j, 1, word)) return true;
                    marked[i][j] = false;
                }
            }
        }

        return false;
    }

    private boolean dfs(int x, int y, int index, String word) {
        int tmp;
        if ((tmp = x-1) >= 0 && doMatch(tmp, y, index, word)) return true;
        if ((tmp = x+1) < xAxis && doMatch(tmp, y, index, word)) return true;
        if ((tmp = y-1) >=0 && doMatch(x, tmp, index, word)) return true;
        if ((tmp = y+1) < yAxis && doMatch(x, tmp, index, word)) return true;
        return false;
    }

    private boolean doMatch(int x, int y, int index, String word) {
        if (board[x][y] == word.charAt(index) && !marked[x][y]) {
            if (index == word.length()-1)
                return true;

            marked[x][y] = true;
            if (dfs(x, y,index+1, word))
                return true;
            marked[x][y] = false;
        }
        return false;
    }


    public static void main(String[] args) {
        Problem79 p = new Problem79();

        char[][] board = {
                {'A', 'B', 'C', 'E'},
                {'S', 'F', 'C', 'S'},
                {'A', 'D', 'E', 'E'}};

        System.out.println("exist 'ABCCED' is " + p.exist(board, "ABCCED"));    // true
        System.out.println("exist 'SEE' is " + p.exist(board, "SEE"));  // true
        System.out.println("exist 'ABCB' is " + p.exist(board, "ABCB"));    // false
        System.out.println("exist 'ASADFBCESCEE' is " + p.exist(board, "ASADFBCESCEE"));    // true
        System.out.println("exist 'ASADFCBESCEE' is " + p.exist(board, "ASADFCBESCEE"));    // false

        board = new char[][]{{'a'}};

        System.out.println("exist 'a' is " + p.exist(board, "a"));    // true

        board = new char[][]{
                {'F','Y','C','E','N','R','D'},
                {'K','L','N','F','I','N','U'},
                {'A','A','A','R','A','H','R'},
                {'N','D','K','L','P','N','E'},
                {'A','L','A','N','S','A','P'},
                {'O','O','G','O','T','P','N'},
                {'H','P','O','L','A','N','O'}};

        System.out.println("exist 'INDIA' is " + p.exist(board, "INDIA"));    // false

    }

}
