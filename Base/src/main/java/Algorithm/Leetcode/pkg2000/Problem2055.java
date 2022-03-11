package Algorithm.Leetcode.pkg2000;

import Algorithm.Leetcode.LeetcodeUtil;

import java.util.*;

/**
 * Created by hfc on 2022/3/8.
 *
 * 2055. 蜡烛之间的盘子
 *
 * 给你一个长桌子，桌子上盘子和蜡烛排成一列。给你一个下标从 0 开始的字符串 s ，它只包含字符 '*' 和 '|' ，
 * 其中 '*' 表示一个 盘子 ，'|' 表示一支 蜡烛 。
 * 同时给你一个下标从 0 开始的二维整数数组 queries ，其中 queries[i] = [lefti, righti] 表示 子字符串 
 * s[lefti...righti] （包含左右端点的字符）。对于每个查询，你需要找到 子字符串中 在 两支蜡烛之间 的盘
 * 子的 数目 。如果一个盘子在 子字符串中 左边和右边 都 至少有一支蜡烛，那么这个盘子满足在 两支蜡烛之间 。
 * - 比方说，s = "||**||**|*" ，查询 [3, 8] ，表示的是子字符串 "*||**|" 。子字符串中在两支蜡烛之间的
 *   盘子数目为 2 ，子字符串中右边两个盘子在它们左边和右边 都 至少有一支蜡烛。
 * 请你返回一个整数数组 answer ，其中 answer[i] 是第 i 个查询的答案。
 *
 * 示例 1:
 * 输入：s = "**|**|***|", queries = [[2,5],[5,9]]
 * 输出：[2,3]
 * 解释：
 * - queries[0] 有两个盘子在蜡烛之间。
 * - queries[1] 有三个盘子在蜡烛之间。
 *
 * 示例 2:
 * 输入：s = "***|**|*****|**||**|*", queries = [[1,17],[4,5],[14,17],[5,11],[15,16]]
 * 输出：[9,0,0,0,0]
 * 解释：
 * - queries[0] 有 9 个盘子在蜡烛之间。
 * - 另一个查询没有盘子在蜡烛之间。
 *
 * 提示：
 * 3 <= s.length <= 10^5
 * s 只包含字符 '*' 和 '|' 。
 * 1 <= queries.length <= 10^5
 * queries[i].length == 2
 * 0 <= lefti <= righti < s.length
 *
 */
public class Problem2055 {

    /**
     * 超出时间限制
     */
    public int[] platesBetweenCandles1(String s, int[][] queries) {
        int[] cntArray = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int[] query = queries[i];
            int plateCnt = 0;
            int tmpCnt = 0;
            boolean hasCandle = false;
            for (int idx = query[0]; idx <= query[1]; idx++) {
                if (s.charAt(idx) == '|') {
                    if (hasCandle) {
                        plateCnt += tmpCnt;
                        tmpCnt = 0;
                    } else {
                        hasCandle = true;
                    }
                } else {
                    if (hasCandle) {
                        tmpCnt++;
                    }
                }
            }
            cntArray[i] = plateCnt;
        }

        return cntArray;
    }

    /**
     * 超出时间限制
     */
    public int[] platesBetweenCandles2(String s, int[][] queries) {
        int[][] grid = new int[s.length()][s.length()];
        boolean hasCandle = false;
        int plateCnt = 0;
        int candleIdx = 0;
        Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '|') {
                if (hasCandle) {
                    grid[candleIdx][i] = plateCnt;
                    queue.add(new int[]{candleIdx, i});
                    plateCnt = 0;
                } else {
                    hasCandle = true;
                }
                candleIdx = i;
            } else {
                if (hasCandle) {
                    plateCnt++;
                }
            }
        }

        while (!queue.isEmpty()) {
            int[] coord = queue.poll();
            int x = coord[0];
            int y = coord[1];
            int initV = grid[x][y];
            grid[x][y] = 0;
            for (; x >= 0; x--) {
                for (int j = y; j < grid[0].length; j++) {
                    grid[x][j] += initV;
                }
            }
        }

        int[] cntArray = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            cntArray[i] = grid[queries[i][0]][queries[i][1]];
        }

        return cntArray;
    }

    /**
     * 题解区给的灵感：前缀和 + 单调栈
     * 速度 34%
     * 内存 33%
     */
    public int[] platesBetweenCandles(String s, int[][] queries) {
        int[] plateCnt = new int[s.length()];
        if (s.charAt(0) == '*') plateCnt[0] = 1;

        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == '*') {
                plateCnt[i] = plateCnt[i - 1] + 1;
            } else {
                plateCnt[i] = plateCnt[i - 1];
            }
        }

        Deque<Integer> stack = new ArrayDeque<>();
        int[] rightCandle = new int[s.length()];
        for (int i = 0; i < s.length(); i++) {
            this.calcCandle(rightCandle, s, i, stack);
        }
        while (!stack.isEmpty()) rightCandle[stack.pop()] = s.length() - 1;

        int[] leftCandle = new int[s.length()];
        for (int i = s.length() - 1; i >= 0; i--) {
            this.calcCandle(leftCandle, s, i, stack);
        }
        while (!stack.isEmpty()) leftCandle[stack.pop()] = 0;

        int[] ret = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int[] query = queries[i];
            int x = rightCandle[query[0]];
            int y = leftCandle[query[1]];
            if (x >= y) ret[i] = 0;
            else ret[i] = plateCnt[y] - plateCnt[x];
        }
        return ret;
    }

    private void calcCandle(int[] candleIdx, String s, int i, Deque<Integer> stack) {
        if (s.charAt(i) == '*') {
            stack.push(i);
        } else {
            candleIdx[i] = i;
            while (!stack.isEmpty()) {
                candleIdx[stack.pop()] = i;
            }
        }
    }

    public static void main(String[] args) {
        Problem2055 p = new Problem2055();

        System.out.println(LeetcodeUtil.equalsOfArray(new int[] {0},
                p.platesBetweenCandles("||*", new int[][] {{2, 2}})));

        System.out.println(LeetcodeUtil.equalsOfArray(new int[] {0, 1, 0, 1, 2},
                p.platesBetweenCandles("*|*|*|*", new int[][] {{1, 2}, {1, 3}, {2, 4}, {2, 5}, {1, 5}})));

        System.out.println(LeetcodeUtil.equalsOfArray(new int[] {2, 3},
                p.platesBetweenCandles("**|**|***|", new int[][] {{2, 5}, {5, 9}})));

        System.out.println(LeetcodeUtil.equalsOfArray(new int[] {9, 0, 0, 0, 0},
                p.platesBetweenCandles("***|**|*****|**||**|*",
                        new int[][] {{1, 17}, {4, 5}, {14, 17}, {5, 11}, {15, 16}})));

        System.out.println(LeetcodeUtil.equalsOfArray(new int[] {0},
                p.platesBetweenCandles("*", new int[][] {{0, 0}})));

        System.out.println(LeetcodeUtil.equalsOfArray(new int[] {0},
                p.platesBetweenCandles("|", new int[][] {{0, 0}})));

        System.out.println(LeetcodeUtil.equalsOfArray(new int[] {0, 0},
                p.platesBetweenCandles("||||||||", new int[][] {{2, 5}, {3, 7}})));

        System.out.println(LeetcodeUtil.equalsOfArray(new int[] {0, 0, 1},
                p.platesBetweenCandles("|||||||*|", new int[][] {{2, 5}, {3, 7}, {4, 8}})));
    }
}
