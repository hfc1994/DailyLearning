package Algorithm.Leetcode;

/**
 * Created by user-hfc on 2020/9/30.
 *
 * 730. 统计不同回文子序列
 *
 * 给定一个字符串 S，找出 S 中不同的非空回文子序列个数，并返回该数字与 10^9 + 7 的模。
 * 通过从 S 中删除 0 个或多个字符来获得子序列。
 * 如果一个字符序列与它反转后的字符序列一致，那么它是回文字符序列。
 * 如果对于某个  i，A_i != B_i，那么 A_1, A_2, ... 和 B_1, B_2, ... 这两个字符序列是不同的。
 *
 * 示例 1：
 * 输入：
 * S = 'bccb'
 * 输出：6
 * 解释：
 * 6 个不同的非空回文子字符序列分别为：'b', 'c', 'bb', 'cc', 'bcb', 'bccb'。
 * 注意：'bcb' 虽然出现两次但仅计数一次。
 *
 * 示例 2：
 * 输入：
 * S = 'abcdabcdabcdabcdabcdabcdabcdabcddcbadcbadcbadcbadcbadcbadcbadcba'
 * 输出：104860361
 * 解释：
 * 共有 3104860382 个不同的非空回文子序列，对 10^9 + 7 取模为 104860361。
 *  
 * 提示：
 * 字符串 S 的长度将在[1, 1000]范围内。
 * 每个字符 S[i] 将会是集合 {'a', 'b', 'c', 'd'} 中的某一个。
 *
 */
public class Problem730 {

    private final char char_a = 'a';

    /**
     * fixme 思路不行
     */
    public int countPalindromicSubsequences(String S) {
        if (S.length() <= 1) return S.length();

        int[][] dp = new int[S.length()][S.length()];
        // maxLength表示最长的回文单词
        int total = 0, maxLength;
        Node head = new Node();
        for (int i=0; i<S.length(); i++) {
            for (int j=0; j<=i; j++) {
                if (i == j || (i-j == 1 && S.charAt(i) == S.charAt(j))) {
                    dp[i][j] = 1;
                } else if (S.charAt(i) == S.charAt(j) && dp[i-1][j+1] != 0) {
                    dp[i][j] = dp[i-1][j+1] + 1;
                    if (addNew(head, S, j, i))
                        total++;
                }
            }
        }

        return total;
    }

    /**
     * 向查找树中添加回文单词
     * 可能出现重复添加，则返回值表示是否有新增
     * @param begin 回文单词起始
     * @param end 回文单词结束
     * @return true：新增；false：未新增
     */
    public boolean addNew(Node node, String src, int begin, int end) {
        int idx;
        for (int i=begin; i<=end; i++) {
            idx = src.charAt(i) - char_a;
            if (node.next[idx] == null)
                node.next[idx] = new Node();

            node = node.next[idx];
            if (i == end) {
                if (node.status) {
                    return false;
                } else {
                    node.status = true;   // 标志，表示某个回文单词的结尾字符，表存在
                    return true;
                }
            }
        }
        return false;
    }

    // 查找树中有没有相应的回文单词
    public boolean find(Node node, String src, int begin, int end) {
        for (int i=begin; i<=end; i++) {
            node = node.next[src.charAt(i) - char_a];
            if (node == null) return false;
            if (i == end && !node.status) return false;
        }
        return true;
    }

    // 为已存在的回文序列构造单词树
    // 对应字符所在数组的索引通过word-'a'来确定
    private class Node {
        Node[] next;
        boolean status;

        public Node() {
            next = new Node[4];
            status = false;
        }
    }

    public static void main(String[] args) {
        Problem730 p = new Problem730();

        String S = "bccb";
        System.out.println(p.countPalindromicSubsequences(S));
    }
}
