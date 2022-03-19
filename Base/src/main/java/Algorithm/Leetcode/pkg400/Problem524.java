package Algorithm.Leetcode.pkg400;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by hfc on 2022/3/14.
 *
 * 524. 通过删除字母匹配到字典里最长单词
 *
 * 给你一个字符串 s 和一个字符串数组 dictionary ，找出并返回 dictionary 中最长的字符串，
 * 该字符串可以通过删除 s 中的某些字符得到。
 * 如果答案不止一个，返回长度最长且字母序最小的字符串。如果答案不存在，则返回空字符串。
 *
 * 示例 1：
 * 输入：s = "abpcplea", dictionary = ["ale","apple","monkey","plea"]
 * 输出："apple"
 *
 * 示例 2：
 * 输入：s = "abpcplea", dictionary = ["a","b","c"]
 * 输出："a"
 *
 * 提示：
 * 1 <= s.length <= 1000
 * 1 <= dictionary.length <= 1000
 * 1 <= dictionary[i].length <= 1000
 * s 和 dictionary[i] 仅由小写英文字母组成
 *
 */
public class Problem524 {

    /**
     * 参考题解
     * 速度 15%
     * 内存 14%
     */
    public String findLongestWord1(String s, List<String> dictionary) {
        List<String> result = new ArrayList<>();
        int maxLength = 0;
        for (String word : dictionary) {
            int idx = 0;
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == word.charAt(idx)) {
                    idx++;
                    if (idx == word.length()) {
                        break;
                    }
                }
            }
            if (idx == word.length()) {
                if (word.length() > maxLength) {
                    maxLength = word.length();
                }
                result.add(word);
            }
        }

        if (result.isEmpty()) {
            return "";
        }
        int finalMaxLength = maxLength;
        result = result.stream()
                .filter(word -> word.length() == finalMaxLength)
                .sorted()
                .collect(Collectors.toList());
        return result.get(0);
    }

    /**
     * 参考题解
     * 速度 41%
     * 内存 7%
     */
    public String findLongestWord(String s, List<String> dictionary) {
        dictionary = dictionary.stream()
                .sorted((w1, w2) -> {
                    if (w1.length() == w2.length()) {
                        return w1.compareTo(w2);
                    } else {
                        return w2.length() - w1.length();
                    }})
                .collect(Collectors.toList());

        String result = null;
        for (String word : dictionary) {
            int idx = 0;
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == word.charAt(idx)) {
                    idx++;
                    if (idx == word.length()) {
                        break;
                    }
                }
            }
            if (idx == word.length()) {
                result = word;
                break;
            }
        }

        return result == null ? "" : result;
    }

    public static void main(String[] args) {
        Problem524 p = new Problem524();

        System.out.println("apple".equals(p.findLongestWord("abpcplea", Arrays.asList("ale","apple","monkey","plea"))));
        System.out.println("a".equals(p.findLongestWord("abpcplea", Arrays.asList("a","b","c"))));
        System.out.println("appla".equals(p.findLongestWord("abpcplea", Arrays.asList("ale","apple","monkey","plea", "appla"))));
        System.out.println("".equals(p.findLongestWord("abpcplea", Arrays.asList("alfe","apfple","monkey"))));
        System.out.println("apple".equals(p.findLongestWord("abpcplea", Arrays.asList("ale","apple","monkey","plea", "abpcplaaa","abpcllllll","abccclllpppeeaaaa"))));
        System.out.println("".equals(p.findLongestWord("abpcplea", Arrays.asList("apaa"))));
        System.out.println("aaa".equals(p.findLongestWord("aaa", Arrays.asList("aaa", "aa", "a"))));
    }
}
