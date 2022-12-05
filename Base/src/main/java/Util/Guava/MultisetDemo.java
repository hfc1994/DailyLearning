package Util.Guava;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import java.util.Arrays;

/**
 * Created by hfc on 2022/12/4.
 *
 * Multiset 是一个 Key 可重复的 Set，重复的 Key 会被统计出现的次数
 * 其实质就是一个 HashMap，Value 就是统计是数量，不断 add 重复的值就相当于在递增 Value
 * 非线程安全
 */
public class MultisetDemo {

    public static void main(String[] args) {
        Multiset<String> multiset = HashMultiset.create();

        // 增加
        multiset.add("A");
        multiset.add("A");
        System.out.println(multiset.size());    // 2
        System.out.println(multiset.count("A"));    // 2
        multiset.add("B");
        multiset.add("C");
        multiset.add("C", 5);
        System.out.println(multiset.size());    // 9
        multiset.addAll(Arrays.asList("D", "E", "F", "D", "G", "E"));
        System.out.println(multiset.size());    // 15


        // 度量
        System.out.println(multiset.elementSet().size());   // 7
        System.out.println(multiset.elementSet());  // [A, B, C, D, E, F, G]

        // 遍历
        multiset.entrySet().forEach(entry -> {
            System.out.print(entry.getElement() + ":" + entry.getCount() + ", ");
        });
        System.out.println();

        multiset.forEachEntry((ele,cnt) -> {
            System.out.print(ele + ":" + cnt + ", ");
        });
        System.out.println();

        multiset.forEach(System.out::print);    // AABCCCCCCDDEEFG
        System.out.println();

        // 移除
        multiset.remove("C");
        System.out.println(multiset.count("C"));    // 5
        multiset.remove("C", 2);
        System.out.println(multiset.count("C"));    // 3
        // 删除Set里在候选中出现的全部，即只出现一个E但删除了两个（全部的E）
        multiset.removeAll(Arrays.asList("D", "E", "F", "D"));
        multiset.forEach(System.out::print);    // AABCCCG
        System.out.println();
        // 保留Set里在候选中出现的
        multiset.retainAll(Arrays.asList("A", "C", "G"));
        multiset.forEach(System.out::print);    // AACCCG
        System.out.println();
        multiset.removeIf("A"::equals);
        multiset.forEach(System.out::print);    // CCCG
        System.out.println();

        // 按需增减元素的数量
        multiset.setCount("A", 3);
        multiset.setCount("G", 2);
        multiset.setCount("C", 1);
        multiset.setCount("B", 0);
        multiset.forEach(System.out::print);    // AAACGG
        System.out.println();

        multiset.setCount("A", 1, 4);
        multiset.setCount("C", 1, 3);
        multiset.forEach(System.out::print);    // AAACCCGG
        System.out.println();
    }

}
