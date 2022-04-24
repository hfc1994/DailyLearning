package Algorithm.Leetcode.pkg800;

import java.util.*;

/**
 * Created by hfc on 2022/4/17.
 *
 * 819. 最常见的单词
 *
 * 给定一个段落 (paragraph) 和一个禁用单词列表 (banned)。返回出现次数最多，
 * 同时不在禁用列表中的单词。
 * 题目保证至少有一个词不在禁用列表中，而且答案唯一。
 * 禁用列表中的单词用小写字母表示，不含标点符号。段落中的单词不区分大小写。答案都是小写字母。
 *
 * 示例：
 * 输入:
 * paragraph = "Bob hit a ball, the hit BALL flew far after it was hit."
 * banned = ["hit"]
 * 输出: "ball"
 * 解释:
 * - "hit" 出现了3次，但它是一个禁用的单词。
 * - "ball" 出现了2次 (同时没有其他单词出现2次)，所以它是段落里出现次数最多的，且不在禁用列表中的单词。
 * - 注意，所有这些单词在段落里不区分大小写，标点符号需要忽略（即使是紧挨着单词也忽略， 比如 "ball,"），
 * - "hit"不是最终的答案，虽然它出现次数更多，但它在禁用单词列表中。
 *
 * 提示：
 * - 1 <= 段落长度 <= 1000
 * - 0 <= 禁用单词个数 <= 100
 * - 1 <= 禁用单词长度 <= 10
 * - 答案是唯一的, 且都是小写字母 (即使在 paragraph 里是大写的，即使是一些特定的名词，答案都是小写的。)
 * - paragraph 只包含字母、空格和下列标点符号!?',;.
 * - 不存在没有连字符或者带有连字符的单词。
 * - 单词里只包含字母，不会出现省略号或者其他标点符号。
 *
 */
public class Problem819 {

    /**
     * 速度 79%
     * 内存 65%
     */
    public String mostCommonWord(String paragraph, String[] banned) {
        Set<String> banSet = new HashSet<>();
        Collections.addAll(banSet, banned);

        int maxCount = 0;
        String maxStr = null;
        Map<String, Integer> stat = new HashMap<>();
        int begin = 0;
        int end = 0;
        boolean isWord = false;
        char[] cs = paragraph.toCharArray();
        for (int idx = 0; idx <= cs.length; idx++) {
            if (idx < cs.length
                    && (this.isLowerLetter(cs[idx]) || this.isUpperLetter(cs[idx]))) {
                if (this.isUpperLetter(cs[idx])) {
                    cs[idx] = (char) (cs[idx] + 32);
                }
                end = idx;
                isWord = true;
            } else {
                if (isWord) {
                    int len = end - begin + 1;
                    char[] tmpC = new char[len];
                    System.arraycopy(cs, begin, tmpC, 0, len);
                    String str = new String(tmpC);
                    if (!banSet.contains(str)) {
                        int count = stat.getOrDefault(str, 0) + 1;
                        stat.put(str, count);
                        if (count > maxCount) {
                            maxCount = count;
                            maxStr = str;
                        }
                    }
                }
                begin = idx + 1;
                isWord = false;
            }
        }

        return maxStr;
    }

    private boolean isLowerLetter(char c) {
        return (c >= 'a' && c <= 'z');
    }

    private boolean isUpperLetter(char c) {
        return (c >= 'A' && c <= 'Z');
    }

    public static void main(String[] args) {
        Problem819 p = new Problem819();

        System.out.println("ball".equals(p.mostCommonWord("Bob hit a ball, the hit BALL flew far after it was hit.",
                new String[] { "hit" })));

        System.out.println("hit".equals(p.mostCommonWord("Bob hit a ball, the hit BALL flew far after it was hit.",
                new String[] { "hita" })));

        System.out.println("a".equals(p.mostCommonWord("a.",
                new String[] { "hit" })));

        System.out.println("bob".equals(p.mostCommonWord("Bob",
                new String[] {})));
    }

}
