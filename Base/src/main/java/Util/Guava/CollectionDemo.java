package Util.Guava;

import com.google.common.collect.*;

import java.util.*;

/**
 * Created by hfc on 2022/12/4.
 */
public class CollectionDemo {

    public static void main(String[] args) {
        Set<String> setA = new HashSet<>(Arrays.asList("A", "B", "C", "D"));
        Set<String> setB = new HashSet<>(Arrays.asList("B", "D", "F", "G", "H"));

        // 返回的是视图 SetView，里面封装了处理逻辑，只有在实时使用
        // 的时候才会进行对应的计算
        // 并集
        System.out.println(Sets.union(setA, setB));
        // 交集
        System.out.println(Sets.intersection(setA, setB));
        // setA 对 setB 的差集
        System.out.println(Sets.difference(setA, setB));
        // setB 对 setA 的差集
        System.out.println(Sets.difference(setB, setA));
        // 对称差集
        System.out.println(Sets.symmetricDifference(setA, setB));

        Map<String, String> mapA = new HashMap<>();
        mapA.put("A", "1");
        mapA.put("B", "2");
        mapA.put("C", "3");
        Map<String, String> mapB = new HashMap<>();
        mapB.put("B", "20");
        mapB.put("C", "3");
        mapB.put("D", "4");

        // 创建一个视图
        MapDifference<String, String> diffMap = Maps.difference(mapA, mapB);
        System.out.println(diffMap.areEqual()); // false
        System.out.println(diffMap.entriesDiffering()); // {B=(2, 20)}
        System.out.println(diffMap.entriesOnlyOnLeft());    // {A=1}
        System.out.println(diffMap.entriesOnlyOnRight());   // {D=4}
        System.out.println(diffMap.entriesInCommon());  // {C=3}
    }

}
