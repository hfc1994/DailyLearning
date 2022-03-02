package Algorithm.Leetcode.pkg0;

/**
 * Created by hfc on 2022/3/1.
 *
 * 6. Z 字形变换
 *
 * 将一个给定字符串 s 根据给定的行数 numRows ，以从上往下、从左到右进行 Z 字形排列。
 * 比如输入字符串为 "PAYPALISHIRING" 行数为 3 时，排列如下：
 *
 * P   A   H   N
 * A P L S I I G
 * Y   I   R
 *
 * 之后，你的输出需要从左往右逐行读取，产生出一个新的字符串，比如："PAHNAPLSIIGYIR"。
 * 请你实现这个将字符串进行指定行数变换的函数：
 * string convert(string s, int numRows);
 *  
 * 示例 1：
 * 输入：s = "PAYPALISHIRING", numRows = 3
 * 输出："PAHNAPLSIIGYIR"
 *
 * 示例 2：
 * 输入：s = "PAYPALISHIRING", numRows = 4
 * 输出："PINALSIGYAHRPI"
 * 解释：
 * P     I    N
 * A   L S  I G
 * Y A   H R
 * P     I
 *
 * 示例 3：
 * 输入：s = "A", numRows = 1
 * 输出："A"
 *
 * 提示：
 * 1 <= s.length <= 1000
 * s 由英文字母（小写和大写）、',' 和 '.' 组成
 * 1 <= numRows <= 1000
 *
 */
public class Problem6 {

    /**
     * 速度 15%
     * 内存 5%
     */
    public String convert1(String s, int numRows) {
        if (numRows >= s.length() || numRows == 1) {
            return s;
        }

        int numCols = ((s.length() - 1) / (2 * numRows - 2) + 1) * (numRows - 1);
        char[][] grid = new char[numRows][numCols];
        boolean isDirect = true;
        int x = 0, y = 0;
        for (char c : s.toCharArray()) {
            grid[x][y] = c;
            if (isDirect) {
                x++;
                if (x == numRows) {
                    x -= 2;
                    y++;
                    if (numRows != 2) {
                        isDirect = false;
                    }
                }
            } else {
                x--;
                y++;
                if (x == 0) {
                    isDirect = true;
                }
            }
        }

        int idx = 0;
        char[] ret = new char[s.length()];
        for (x = 0; x < grid.length; x++) {
            for (y = 0; y < grid[0].length; y++) {
                if (grid[x][y] > 0) {
                    ret[idx++] = grid[x][y];
                }
            }
        }
        return new String(ret);
    }

    /**
     * 速度 23%
     * 内存 33%
     */
    public String convert(String s, int numRows) {
        if (numRows >= s.length() || numRows == 1) {
            return s;
        }

        char[] cs = new char[s.length()];
        boolean[] mark = new boolean[s.length()];
        int retIdx = 0;
        int idx = 0;
        for (int row = numRows; row >= 1; row--) {
            int interval = (row - 1) * 2;
            while (idx < s.length() && retIdx < s.length()) {
                cs[retIdx++] = s.charAt(idx);
                mark[idx] = true;

                int tmpIdx = idx + 1;
                while (tmpIdx < s.length() && mark[tmpIdx]) {
                    tmpIdx++;
                    if (tmpIdx < s.length() && !mark[tmpIdx]) {
                        idx = tmpIdx++;
                        cs[retIdx++] = s.charAt(idx);
                        mark[idx] = true;
                    }
                    if (tmpIdx == s.length()) idx = tmpIdx;
                }

                idx += interval;
            }

            for (int i = 0; i < mark.length; i++) {
                if (!mark[i]) {
                    idx = i;
                    break;
                }
            }
        }

        return new String(cs);
    }

    public static void main(String[] args) {
        Problem6 p = new Problem6();

        System.out.println("PAHNAPLSIIGYIR".equals(p.convert("PAYPALISHIRING", 3)));
        System.out.println("PINALSIGYAHRPI".equals(p.convert("PAYPALISHIRING", 4)));
        System.out.println("A".equals(p.convert("A", 1)));
        System.out.println("AB".equals(p.convert("AB", 1)));
        System.out.println("PINALSIGDYAHRACPIB".equals(p.convert("PAYPALISHIRINGABCD", 4)));
        System.out.println(p.convert("PAYPALISHIRINGABCD", 2));
        System.out.println(p.convert("PAYPALISHIRINGABCD", 3));
        System.out.println(p.convert("PAYPALISHIRINGABCD", 4));
        System.out.println(p.convert("PAYPALISHIRINGABCD", 5));
        System.out.println(p.convert("PAYPALISHIRINGABCD", 6));
    }
}
