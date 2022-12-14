package Util.Guava;

import com.google.common.collect.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by hfc on 2022/12/12.
 *
 * 常规用法
 */
public class GuavaDemo {

    public static void main(String[] args) {
        // 创建集合
        List<String> list = Lists.newArrayList();
        List<Integer> intList = Lists.newArrayList();
        Set<String> set = Sets.newHashSet("A", "B");
        Map<String, Integer> map = Maps.newHashMap();

        // 创建不可变集合
        ImmutableList<String> iList = ImmutableList.of("A", "B", "C");
        ImmutableSet<String> iSet = ImmutableSet.of("A", "B", "C");
        ImmutableMap<String, String> iMap = ImmutableMap.of("K1", "V1");
    }

}
