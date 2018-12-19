package Lambda;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by user-hfc on 2018/11/5.
 *
 * @author user-hfc.
 */
public class lambdas2
{

    public static void main(String[] args)
    {
        String[] provicesArray = {"浙江","江苏","重庆","广东","福建","北京","安徽"};
        String[] stupidStr = {"a","saa","xczs","sasdas","wqe","xcvxc","fsfewrw"," cxcv"};

        List<String> provicesList = Arrays.asList(provicesArray);
        List<String> strList = Arrays.asList(stupidStr);

//        Stream stream = provicesList.stream().filter(provice -> {
//            System.out.println("---" + provice + "---");
//            return provice.length() > 1;
//        });
//
//        System.out.println("----");
//        System.out.println(stream.count());
//
//        System.out.println("----");
//
//        List<String> collected = Stream.of("a","b","hello")
//                .map(string -> string.toUpperCase())
//                .collect(Collectors.toList());
//
//        System.out.println("----");

//        List<Integer> together = Stream.of(Arrays.asList(1,4), Arrays.asList(3,2))
//                .flatMap(numbers -> {
//                    System.out.println(numbers);
//                    return numbers.stream();
//                }).collect(Collectors.toList());
//        System.out.println(together);

        System.out.println("----");
        String mini = strList.stream().min(Comparator.comparing(str -> str.length())).get();
        String maxm = strList.stream().max(Comparator.comparing(str -> str.length())).get();
        System.out.println("mini = " + mini);
        System.out.println("maxm = " + maxm);
    }
}
