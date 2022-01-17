package Algorithm.Leetcode.pkg0;

/**
 * Created by hfc on 2022/1/11.
 *
 * 5. 最长回文子串
 *
 * 给你一个字符串 s，找到 s 中最长的回文子串。
 *
 * 示例 1：
 * 输入：s = "babad"
 * 输出："bab"
 * 解释："aba" 同样是符合题意的答案。
 *
 * 示例 2：
 * 输入：s = "cbbd"
 * 输出："bb"
 *
 * 示例 3：
 * 输入：s = "a"
 * 输出："a"
 *
 * 示例 4：
 * 输入：s = "ac"
 * 输出："a"
 *
 * 提示：
 * 1 <= s.length <= 1000
 * s 仅由数字和英文字母（大写和/或小写）组成
 */
public class Problem5 {

    /**
     * 暴力计算，速度和内存较差
     */
    public String longestPalindrome1(String s) {
        if (s.length() <= 1) return s;

        String maxStr = "";
        for (int i = 0; i < s.length(); i++) {
            maxStr = this.doSearch(s, i, i, maxStr);

            if (i + 1 < s.length() && s.charAt(i) == s.charAt(i + 1)) {
                maxStr = this.doSearch(s, i, i+1, maxStr);
            }
        }
        return maxStr;
    }

    private String doSearch(String s, int lIdx, int rIdx, String maxStr) {
        int maxLIdx = 0, maxRIdx = 0;
        if (maxStr.length() == 0 || maxStr.length() < (rIdx - lIdx + 1)) {
            maxLIdx = lIdx;
            maxRIdx = rIdx;
        } else {
            maxRIdx = maxStr.length() - 1;
        }

        lIdx--;
        rIdx++;
        while (lIdx >= 0 && rIdx < s.length()) {
            if (s.charAt(lIdx) == s.charAt(rIdx)) {
                int len = rIdx - lIdx + 1;
                if (len > (maxRIdx - maxLIdx)) {
                    maxLIdx = lIdx;
                    maxRIdx = rIdx;
                }

                lIdx--;
                rIdx++;
            } else {
                break;
            }
        }
        return (maxStr.length() == (maxRIdx - maxLIdx + 1))
                ? maxStr : s.substring(maxLIdx, maxRIdx + 1);
    }

    /**
     * 在遭遇类似有几十个 a 的场景会时间超限
     */
    public String longestPalindrome2(String s) {
        if (s.length() <= 1) return s;

        boolean[][] dp = new boolean[s.length()][s.length()];

        for (int i = 0; i < s.length(); i++) {
            for (int j = i; j < s.length(); j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = true;
                }
            }
        }

        String maxStr = "";
        for (int i = 0; i < s.length(); i++) {
            for (int j = i; j < s.length(); j++) {
                if (isPalindrome(dp, i, j) && j - i + 1 > maxStr.length()) {
                    maxStr = s.substring(i, j + 1);
                }
            }
        }
        return maxStr;
    }

    private boolean isPalindrome(boolean[][] dp, int left, int right) {
        if (left == right || left + 1 == right) {
            return dp[left][right];
        } else {
            return dp[left][right] && isPalindrome(dp, left + 1, right - 1);
        }
    }

    /**
     * 动态规划法，但速度内存居然并不高。。。
     */
    public String longestPalindrome(String s) {
        if (s.length() < 2) return s;

        boolean[][] dp = new boolean[s.length()][s.length()];
        String maxStr = "";
        for (int j = 0; j < s.length(); j++) {
            for (int i = j; i >= 0; i--) {
                if (j == i) {
                    dp[i][j] = true;
                } else if (s.charAt(j) == s.charAt(i)) {
                    if (j - i > 1) {
                        dp[i][j] = dp[i+1][j-1];
                    } else {
                        dp[i][j] = true;
                    }
                }

                if (dp[i][j] && j - i + 1 > maxStr.length()) {
                    maxStr = s.substring(i, j + 1);
                }
            }
        }
        return maxStr;
    }

    public static void main(String[] args) {
        Problem5 p = new Problem5();

        System.out.println(p.longestPalindrome("babad"));
        System.out.println(p.longestPalindrome("cbbd"));
        System.out.println(p.longestPalindrome("ac"));
        System.out.println(p.longestPalindrome("a"));
        System.out.println(p.longestPalindrome("aa"));
        System.out.println(p.longestPalindrome("aaa"));
        System.out.println(p.longestPalindrome("aaaa"));
        System.out.println(p.longestPalindrome("aabaa"));
        System.out.println(p.longestPalindrome("caba"));
        System.out.println(p.longestPalindrome("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
    }
}
