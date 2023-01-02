package Util.Guava;

import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Ordering;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hfc on 2022/12/20.
 */
public class StringDemo {

    public static void main(String[] args) {
        //
        // 字符串拼接
        //
        StringBuilder sb = new StringBuilder();
        Joiner.on("|")
                .skipNulls()
                .appendTo(sb, "A", "B", "C", null, "D");
        System.out.println(sb);    // A|B|C|D

        sb = new StringBuilder();
        Joiner.on("|")
                .useForNull("NullStr")
                .appendTo(sb, "A", "B", "C", null, "D");
        System.out.println(sb);    // A|B|C|NullStr|D

        Joiner j1 = Joiner.on("|");
        Map<String, Integer> oMap = new HashMap<>();
        oMap.put("AA", 1);
        oMap.put("BB", 2);
        String result = j1.withKeyValueSeparator("#").join(oMap);
        System.out.println(result); // AA#1|BB#2

        //
        // 字符串分割
        //
        Splitter sp = Splitter.on("|");
        List<String> ret = sp.splitToList("a | b|c|| |d |e");
        System.out.println(ret.size()); // 7
        printListToLine(ret);  // a , b,c,, ,d ,e,

        // 去掉元素前后的空白
        ret = Splitter.on("|")
                .trimResults()
                .splitToList("a | b|c|| |d |e");
        System.out.println(ret.size()); // 7
        printListToLine(ret);  // a,b,c,,,d,e,

        // 多余的分隔符
        ret = Splitter.on("|")
                .omitEmptyStrings()
                .splitToList("a | b|c|| |d |e");
        System.out.println(ret.size()); // 6
        printListToLine(ret);  // a , b,c, ,d ,e,

        // 忽略掉字串中的空白和多余的分隔符
        ret = Splitter.on("|")
                .omitEmptyStrings()
                .trimResults()
                .splitToList("a | b|c|| |d |e");
        System.out.println(ret.size()); // 5
        printListToLine(ret);  // a,b,c,d,e,

        // 固定长度来分割
        ret = Splitter.fixedLength(3)
                .splitToList("abcdefghijk");
        printListToLine(ret);  // abc,def,ghi,jk,

        // 分割到指定个数
        ret = Splitter.on("|")
                .limit(4)
                .splitToList("a|bc|def|ghij|kl");
        printListToLine(ret);  // abc,def,ghi,jk,

        // 正则表达式分割
        ret = Splitter.onPattern("[0-9]")
                .splitToList("ab1cd2ef3gh");
        printListToLine(ret);   // ab,cd,ef,gh,

        Map<String, String> sMap = Splitter.on("|")
                .withKeyValueSeparator("=")
                .split("a=1|b=2|c=3");
        System.out.println(sMap);   // {a=1, b=2, c=3}

        //
        // 字符匹配器
        //
        String origin = "hello world\r\ryou are here\ntake it\t easy";
        // 去除空白
        String strRet = CharMatcher.breakingWhitespace()
                .removeFrom(origin);
        System.out.println(strRet);
        // 把空白替换成*
        strRet = CharMatcher.breakingWhitespace()
                .replaceFrom(origin, "*");
        System.out.println(strRet);
        // 把空白替换成*，多个空白合并成一个
        strRet = CharMatcher.breakingWhitespace()
                .collapseFrom(origin, '*');
        System.out.println(strRet);

        //
        // 排序器
        //
        List<String> strList = Arrays.asList("hello", "world", "you", null,
                "are", "here", "hello", "take", "world", "it", null, "easy", "you");
        Ordering<String> ordering = Ordering.natural()
                .reverse()
                .nullsFirst();
        System.out.println(Ordering.natural().nullsFirst().isOrdered(strList));
        System.out.println(Ordering.natural().reverse().nullsFirst().isOrdered(strList));
        strList.sort(ordering);
        System.out.println(strList);
        System.out.println(Ordering.natural().nullsFirst().isOrdered(strList));
        System.out.println(Ordering.natural().reverse().nullsFirst().isOrdered(strList));
    }

    private static void printListToLine(List<String> strs) {
        for (String str : strs) {
            System.out.print(str + ",");
        }
        System.out.println();
    }

}
