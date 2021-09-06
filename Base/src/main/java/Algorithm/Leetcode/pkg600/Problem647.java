package Algorithm.Leetcode.pkg600;

/**
 * Created by hfc on 2020/8/28.
 *
 * 647. 回文子串
 *
 * 给定一个字符串，你的任务是计算这个字符串中有多少个回文子串。
 * 具有不同开始位置或结束位置的子串，即使是由相同的字符组成，也会被视作不同的子串。
 *
 * 示例 1：
 * 输入："abc"
 * 输出：3
 * 解释：三个回文子串: "a", "b", "c"
 *
 * 示例 2：
 * 输入："aaa"
 * 输出：6
 * 解释：6个回文子串: "a", "a", "a", "aa", "aa", "aaa"
 *  
 * 提示：
 * 输入的字符串长度不会超过 1000 。
 *
 */
public class Problem647 {

    public int countSubstrings1(String s) {
        int subCount = 0;
        for (int subLength=1; subLength<=s.length(); subLength++) {
            for (int i=0; i<=s.length()-subLength; i++) {
                if (isTargetString(s, i, i + subLength)) {
                    subCount++;
                }
            }
        }
        return subCount;
    }

    // 动态规划方式
    public int countSubstrings2(String s) {
        int count= 0;
        boolean[][] flags = new boolean[s.length()][s.length()];

        for (int i=0; i<flags.length; i++) {
            for (int j=0; j<=i; j++) {
                if (i == j) {
                    count++;
                    flags[i][j] = true;
                } else if (i-j==1 && s.charAt(i) == s.charAt(j)) {
                    count++;
                    flags[i][j] = true;
                } else if (s.charAt(i) == s.charAt(j) && flags[i-1][j+1]) {
                    count++;
                    flags[i][j] = true;
                }
            }
        }
        return count;
    }

    // 动态规划精简
    public int countSubstrings3(String s) {
        int count= 0;
        boolean[][] flags = new boolean[s.length()][s.length()];

        for (int i=0; i<flags.length; i++) {
            for (int j=0; j<=i; j++) {
                if (i == j || (s.charAt(i) == s.charAt(j) && (i-j==1 || flags[i-1][j+1]))) {
                    count++;
                    flags[i][j] = true;
                }
            }
        }
        return count;
    }

    // 判断是否是回文字符串
    private boolean isTargetString(String s, int begin, int end) {
        int length = end - begin;
        for (int i=0; i<length/2; i++) {
            if (s.charAt(begin + i) != s.charAt(end - 1 - i))
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        Problem647 p = new Problem647();

        System.out.println(p.countSubstrings1("aaa"));
        System.out.println(p.countSubstrings2("aaa"));
        System.out.println(p.countSubstrings3("aaa"));
    }
}
