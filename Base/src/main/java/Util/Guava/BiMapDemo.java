package Util.Guava;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * Created by hfc on 2022/12/7.
 *
 * BiMap 是 key 和 value 双向关联的数据结构。
 * BiMap 在写入时既会要求 key 的唯一性，也会要求 value 的唯一性。
 * 如果 key 重复，value 会被覆盖；如果 value 重复，则会抛出 java.lang.IllegalArgumentException的异常，但是也可以
 * 使用 forcePut() 方法来强制插入，但是这个时候 key 也会被新值覆盖。
 * 举例：用来存储国家和首都，可以国家找首都，也能首都找国家。
 *
 * 维护了 2 个 BiEntry 数组，一个用于从 keyHash 查 BiEntry，另一个用于从 valueHash 查 BiEntry
 * 每次写入、更新等操作均会检查两个 BiEntry 数组
 *
 */
public class BiMapDemo {

    public static void main(String[] args) {
        BiMap<String, Integer> biMap = HashBiMap.create();

        biMap.put("A", 1);
        biMap.put("B", 2);
        biMap.put("C", 3);
        System.out.println(biMap.get("A")); // 1
        System.out.println(biMap.get("B")); // 2
        System.out.println(biMap);  // {A=1, B=2, C=3}

        Integer oldV = biMap.put("A", 4);
        System.out.println("oldValue=" + oldV); // 1
        System.out.println(biMap.get("A")); // 4

        System.out.println(biMap.get("B")); // 2
        biMap.forcePut("D", 2);
        System.out.println(biMap.get("D")); // 2
        // 强制更新重复的 value 值会导致原先的 key 也被覆盖
        System.out.println(biMap.get("B")); // null

        // 反转的 BiMap 不是新 map，而是已有 BiMap 的一个视图关联
        // 只不过当前视图的 key 是原先的 value
        // 反转后和反转前的数据是同一份，还是在同样的 2 个 BiEntry 上操作
        BiMap<Integer, String> inverseMap = biMap.inverse();
        System.out.println(inverseMap.get(2));  // D

    }

}
