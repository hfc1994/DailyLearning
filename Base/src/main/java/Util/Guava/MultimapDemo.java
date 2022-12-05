package Util.Guava;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.Arrays;

/**
 * Created by hfc on 2022/12/4.
 *
 * 相当于 Value 固定是一个链表的 Map 结构，对同一个 Key 的多次 put 均会把
 * 值放置到对应的链表中。即使是相同的值，多次 put 后移除时就需要相同的次数
 *
 * 其本质也可以认为就是一个 Value 是 Collection 的 HashMap
 *
 */
public class MultimapDemo {

    public static void main(String[] args) {
        Multimap<String, Integer> multimap = ArrayListMultimap.create();

        // 添加
        multimap.put("A", 1);
        multimap.put("A", 1);   // 重复添加后需要多次 remove 才能清除
        multimap.put("A", 2);
        multimap.put("A", 3);
        multimap.putAll("B", Arrays.asList(4, 5, 6));

        System.out.println(multimap.size());    // 7
        System.out.println(multimap.asMap());   // {A=[1, 1, 2, 3], B=[4, 5, 6]}
        System.out.println(multimap.keySet());  // [A, B]
        System.out.println(multimap.keys());    // [A x 4, B x 3]
        System.out.println(multimap.get("B"));  // [4, 5, 6]

        // 移除
        System.out.println(multimap.remove("A", 1));    // true
        System.out.println(multimap.remove("B", 4));    // true
        System.out.println(multimap);   // {A=[1, 2, 3], B=[5, 6]}
        System.out.println(multimap.removeAll("B"));    // [5, 6]
        System.out.println(multimap);   // {A=[1, 2, 3]}

        // 判断
        multimap.putAll("B", Arrays.asList(4, 5, 6));
        multimap.putAll("C", Arrays.asList(7, 8, 9));
        System.out.println(multimap.containsEntry("A", 2)); // true
        System.out.println(multimap.containsKey("B"));  // true
        System.out.println(multimap.containsKey("D"));  // false
        System.out.println(multimap.containsValue(8));  // true
        System.out.println(multimap.containsValue(18)); // false

        // 遍历
        // A:1, A:2, A:3, B:4, B:5, B:6, C:7, C:8, C:9,
        multimap.forEach((key, val) -> System.out.print(key + ":" + val + ", "));
        System.out.println();
        // A:1, A:2, A:3, B:4, B:5, B:6, C:7, C:8, C:9,
        multimap.entries().forEach(entry -> System.out.print(entry.getKey() + ":" + entry.getValue() + ", "));
        System.out.println();

        multimap.replaceValues("C", Arrays.asList(10, 11)); // {A=[1, 2, 3], B=[4, 5, 6], C=[10, 11]}
        System.out.println(multimap);
    }

}
