package Algorithm.Leetcode.pkg600;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by hfc on 2022/3/17.
 *
 * 720. 词典中最长的单词
 *
 * 给出一个字符串数组 words 组成的一本英语词典。返回 words 中最长的一个单词，
 * 该单词是由 words 词典中其他单词逐步添加一个字母组成。
 * 若其中有多个可行的答案，则返回答案中字典序最小的单词。若无答案，则返回空字符串。
 *
 * 示例 1：
 * 输入：words = ["w","wo","wor","worl", "world"]
 * 输出："world"
 * 解释： 单词"world"可由"w", "wo", "wor", 和 "worl"逐步添加一个字母组成。
 *
 * 示例 2：
 * 输入：words = ["a", "banana", "app", "appl", "ap", "apply", "apple"]
 * 输出："apple"
 * 解释："apply" 和 "apple" 都能由词典中的单词组成。但是 "apple" 的字典序小于 "apply"
 *
 * 提示：
 * 1 <= words.length <= 1000
 * 1 <= words[i].length <= 30
 * 所有输入的字符串 words[i] 都只包含小写字母。
 *
 */
public class Problem720 {

    /**
     * 字典树，评论区给的灵感
     * 个人感觉题目给出的条件有点含糊了
     * 速度 16%
     * 内存 11%
     */
    public String longestWord1(String[] words) {
        if (words.length == 1) {
            return words[0];
        }

        words = Stream.of(words)
                .sorted(Comparator.comparingInt(String::length))
                .toArray(String[]::new);

        int maxLen = 0;
        List<String> result = new ArrayList<>();
        Node root = new Node();
        for (String word : words) {
            Node cur = root;
            for (int i = 0; i < word.length(); i++) {
                int idx = word.charAt(i) - 'a';
                if (cur.children[idx] != null) {
                    cur = cur.children[idx];
                } else if (cur.children[idx] == null && i == word.length() - 1) {
                    cur.children[idx] = new Node();
                    cur = cur.children[idx];
                } else {
                    break;
                }

                if (i == word.length() - 1) {
                    if (word.length() > maxLen) {
                        maxLen = word.length();
                        result.clear();
                        result.add(word);
                    } else if (word.length() == maxLen) {
                        result.add(word);
                    }
                }
            }
        }

        return result.size() == 0
                ? ""
                : result.stream().sorted().collect(Collectors.toList()).get(0);
    }

    class Node {
        Node[] children;

        public Node() {
            this.children = new Node[26];
        }
    }

    /**
     * 来自题解
     */
    public String longestWord(String[] words) {
        Arrays.sort(words, (a, b) ->  {
            if (a.length() != b.length()) {
                return a.length() - b.length();
            } else {
                // 字母序从大到小
                return b.compareTo(a);
            }
        });
        String longest = "";
        Set<String> candidates = new HashSet<>();
        candidates.add("");
        int n = words.length;
        for (int i = 0; i < n; i++) {
            String word = words[i];
            if (candidates.contains(word.substring(0, word.length() - 1))) {
                candidates.add(word);
                longest = word;
            }
        }
        return longest;
    }

    public static void main(String[] args) {
        Problem720 p = new Problem720();

        System.out.println("".equals(p.longestWord(new String[] {"wo", "wor", "worl", "world"})));
        System.out.println("world".equals(p.longestWord(new String[] {"w", "wo", "wor", "worl", "world"})));
        System.out.println("apple".equals(p.longestWord(new String[] {"a", "banana", "app", "appl", "ap", "apply", "apple"})));
        System.out.println("apaa".equals(p.longestWord(new String[] {"a", "ap", "app", "apa", "apad", "apae", "apaa"})));
        System.out.println("appa".equals(p.longestWord(new String[] {"a", "ap", "app", "apr", "aprd", "appa", "apre", "apra"})));
    }
}
