package Algorithm.Leetcode.pkg800;

import Algorithm.Leetcode.LeetcodeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by hfc on 2022/1/30.
 *
 * 884. 两句话中的不常见单词
 *
 * 句子 是一串由空格分隔的单词。每个 单词 仅由小写字母组成。
 * 如果某个单词在其中一个句子中恰好出现一次，在另一个句子中却 没有出现 ，
 * 那么这个单词就是 不常见的 。
 * 给你两个 句子 s1 和 s2 ，返回所有 不常用单词 的列表。返回列表中单词
 * 可以按任意顺序 组织。
 *
 * 示例 1：
 * 输入：s1 = "this apple is sweet", s2 = "this apple is sour"
 * 输出：["sweet","sour"]
 *
 * 示例 2：
 * 输入：s1 = "apple apple", s2 = "banana"
 * 输出：["banana"]
 *
 * 提示：
 * 1 <= s1.length, s2.length <= 200
 * s1 和 s2 由小写英文字母和空格组成
 * s1 和 s2 都不含前导或尾随空格
 * s1 和 s2 中的所有单词间均由单个空格分隔
 */
public class Problem884 {

    /**
     * 内存与速度略差
     */
    public String[] uncommonFromSentences1(String s1, String s2) {
        Map<String, Integer> stat = new HashMap<>();
        Stream.concat(Stream.of(s1.split(" ")), Stream.of(s2.split(" ")))
                .forEach(str -> {
                    int cnt = stat.getOrDefault(str, 0);
                    stat.put(str, ++cnt);
                });

        List<String> list = new ArrayList<>();
        stat.forEach((key, val) -> {
            if (val == 1) {
                list.add(key);
            }
        });

        return list.toArray(new String[0]);
    }

    public String[] uncommonFromSentences2(String s1, String s2) {
        Map<String, Integer> stat = new HashMap<>();
        for (String str : s1.split(" ")) {
            int cnt = stat.getOrDefault(str, 0);
            stat.put(str, ++cnt);
        }

        for (String str : s2.split(" ")) {
            int cnt = stat.getOrDefault(str, 0);
            stat.put(str, ++cnt);
        }

        List<String> list = new ArrayList<>();
        stat.forEach((key, val) -> {
            if (val == 1) {
                list.add(key);
            }
        });

        return list.toArray(new String[0]);
    }

    public String[] uncommonFromSentences(String s1, String s2) {
        Map<String, Integer> stat = new HashMap<>();
        this.doScan(s1, stat);
        this.doScan(s2, stat);

        List<String> list = new ArrayList<>();
        stat.forEach((key, val) -> {
            if (val == 1) {
                list.add(key);
            }
        });

        return list.toArray(new String[0]);
    }

    private void doScan(String str, Map<String, Integer> stat) {
        int slow = 0;
        for (int fast = 1; fast <= str.length(); fast++) {
            if (fast == str.length() || str.charAt(fast) == ' ') {
                if (fast != slow) {
                    String tmp = str.substring(slow, fast);
                    stat.put(tmp, stat.getOrDefault(tmp, 0) + 1);
                }
                slow = fast + 1;
            }
        }
    }

    public static void main(String[] args) {
        Problem884 p = new Problem884();

        boolean cpRet = LeetcodeUtil.equalsOfArray(p.uncommonFromSentences("this apple is sweet", "this apple is sour"),
                new String[] {"sweet", "sour"});
        System.out.println(cpRet);

        cpRet = LeetcodeUtil.equalsOfArray(p.uncommonFromSentences("apple apple", "banana"),
                new String[] {"banana"});
        System.out.println(cpRet);
    }


}
