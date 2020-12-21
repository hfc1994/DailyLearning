package Lambda;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by hfc on 2020/12/4.
 *
 * Lambda的用法练习
 */
public class Practise {

    public static void main(String[] args) {
        List<Employe> empList = new ArrayList<Employe>();
        empList.add(new Employe("Tom", 8900, 25, "male", "New York"));
        empList.add(new Employe("Jack", 7000, 27, "male", "Washington"));
        empList.add(new Employe("Lily", 7800, 25, "female", "Washington"));
        empList.add(new Employe("Anni", 8200, 26, "female", "New York"));
        empList.add(new Employe("Owen", 9500, 28, "male", "New York"));
        empList.add(new Employe("Alisa", 7900, 26, "female", "New York"));

        // 使用Stream的静态方法
        Stream.of(1, 2, 3, 4, 5, 6)
                .forEach(System.out::println);   // 1 2 3 4 5 6

        Stream.iterate(0, x -> x+3)
                .limit(4)
                .forEach(System.out::println);   // 0 3 6 9

        Stream.iterate(0, x -> x+3)
                .skip(3)
                .limit(4)
                .forEach(System.out::println);  // 9 12 15 18

        // 随机生成三个数
        Stream.generate(Math::random)
                .limit(3)
                .forEach(System.out::println);

        List<Integer> list = Arrays.asList(9, 8, 5, 9, 4, 9, 2, 1, 0);
        list.stream()
                .filter(x -> x>6)
                .forEach(System.out::println);

        // 两组数据去重后合并
        List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5, 6);
        Stream.concat(Stream.of(list), Stream.of(list1))
                .distinct()
                .collect(Collectors.toList())
                .forEach(System.out::println);


        // 获取最大值
        list.stream()
                .max(Integer::compareTo)
                .ifPresent(System.out::println);

        // 匹配第一个
        list.stream()
                .filter(x -> x>6)
                .findFirst()
                .ifPresent(System.out::println);

        // 匹配任意一个（适用于并行流）
        list.parallelStream()
                .filter(x -> x>6)
                .findAny()
                .ifPresent(System.out::println);

        // 是否包含复合特定条件的元素
        boolean exist = list.stream()
                .anyMatch(x -> x>6);
        System.out.println(exist);

        // 筛选员工中工资高于8000的人的名字，并形成新的集合
        empList.stream()
                .filter(p -> p.getSalary() > 8000)
                .map(Employe::getName)
                .collect(Collectors.toList())
                .forEach(System.out::println);

        // 统计员工中工资高于8000的人数
        long count = empList.stream()
                .filter(p -> p.getSalary()>8000)
                .count();
        System.out.println(count);

        // 获取员工中所在地名称最长的
        empList.stream()
                .map(Employe::getArea)
                .max(Comparator.comparing(String::length))
                .ifPresent(System.out::println);

        // 获取员工工资最高的人
        empList.stream()
                .max(Comparator.comparingInt(Employe::getSalary))
                .map(Employe::getName)
                .ifPresent(System.out::println);

        // 员工所处地区转为大写
        empList.stream()
                .map(p -> p.getArea().toUpperCase())
                .forEach(System.out::println);

        // 将员工的收入加10
        // 修改原数据 - 写法1
        empList.stream()
                .map(p -> {
                    p.setSalary(p.getSalary() + 10);
                    return p;
                }).map(Employe::getSalary)
                .forEach(System.out::println);
        // 修改原数据 - 写法2
        empList.stream()
                .peek(p -> p.setSalary(p.getSalary() + 10))
                .map(Employe::getSalary)
                .forEach(System.out::println);
        // 不修改原数据，返回新数组
        empList.stream()
                .map(p -> new Employe(p.getName(),
                        p.getSalary() + 10,
                        p.getAge(),
                        p.getSex(),
                        p.getArea()))
                .map(Employe::getSalary)
                .forEach(System.out::println);

        // flatMap可以把多个流合并为一个流
        // 输出全部员工名字的所有字母
        empList.stream().flatMap(p -> {
            String[] content = p.getName().split("");
            return Stream.of(content);
        }).forEach(System.out::print);
        System.out.println();

        // reduce可以把一个流归约为一个流
        // 求和1
        list.stream()
                .reduce((x, y) -> x+y)
                .ifPresent(System.out::println);
        // 求和2
        list.stream()
                .reduce(Integer::sum)
                .ifPresent(System.out::println);
        // 求和3
        int sum = list.stream()
                .reduce(0, (x, y) -> x+y);
        System.out.println(sum);

        // 求最大值1
        list.stream()
                .reduce((x, y) -> x>y ? x : y)
                .ifPresent(System.out::println);
        // 求最大值2
        int max = list.stream()
                .reduce(Integer.MIN_VALUE, Integer::max);
        System.out.println(max);

        // 求所有员工的工资之和以及最高工资
        // 工资之和 - 方式1
        empList.stream()
                .map(Employe::getSalary)
                .reduce(Integer::sum)
                .ifPresent(System.out::println);
        // 工资之和 - 方式2
        sum = empList.stream()
                .reduce(0, (sum0, p) -> sum0 += p.getSalary(), (sum1, sum2) -> sum1+sum2);
        System.out.println(sum);
        // 工资之和 - 方式3
        sum = empList.stream()
                .reduce(0, (sum0, p) -> sum0 += p.getSalary(), Integer::sum);
        System.out.println(sum);

        // 求员工工资最大值
        max = empList.stream()
                .reduce(0, (max0, p) -> max0 > p.getSalary() ? max0 : p.getSalary(), Integer::max);
        System.out.println(max);

        // 获取工资大于8000的员工信息，存放在一个map里，以员工名为key
        int size = empList.stream()
                .filter(p -> p.getSalary() > 8000)
                .collect(Collectors.toMap(Employe::getName, p -> p))
                .size();
        System.out.println(size);

        // 统计工资大于8000的员工人数
        count = empList.stream()
                .filter(p -> p.getSalary() > 8000)
                .collect(Collectors.counting());
        System.out.println(count);

        // 统计员工的平均工资
        double avg = empList.stream()
                .collect(Collectors.averagingDouble(Employe::getSalary));
        System.out.println(avg);

        // 求最高工资
        empList.stream()
                .map(Employe::getSalary)
                .collect(Collectors.maxBy(Integer::compareTo))
                .ifPresent(System.out::println);

        // 求工资之和
        sum = empList.stream()
                .collect(Collectors.summingInt(Employe::getSalary));
        System.out.println(sum);

        // 获取统计信息
        DoubleSummaryStatistics stat = empList.stream()
                .collect(Collectors.summarizingDouble(Employe::getSalary));
        System.out.println("max = " + stat.getMax());
        System.out.println("avg = " + stat.getAverage());

        // 把员工工资是否大于8000来分组
        Map<Boolean, List<Employe>> oMap1 = empList.stream()
                .collect(Collectors.partitioningBy(p -> p.getSalary() > 8000));

        // 把员工按照性别来分组
        Map<String, List<Employe>> oMap2 = empList.stream()
                .collect(Collectors.groupingBy(Employe::getSex));

        // 把员工先按照性别分组，再按照地区分组
        Map<String, Map<String, List<Employe>>> oMap3 = empList.stream()
                .collect(Collectors.groupingBy(Employe::getSex, Collectors.groupingBy(Employe::getArea)));

        // 输出所有员工的名字，并使用“,”分隔
        String joins = empList.stream()
                .map(Employe::getName)
                .collect(Collectors.joining(","));
        System.out.println(joins);

        // 按工资升序排序（自然排序）
        empList.stream()
                .sorted(Comparator.comparing(Employe::getSalary))
                .map(Employe::getName)
                .forEach(System.out::println);
        // 按工资降序序排序（自然排序）
        empList.stream()
                .sorted(Comparator.comparing(Employe::getSalary).reversed())
                .map(Employe::getName)
                .forEach(System.out::println);

        // 先按工资再按年龄升序排序
        empList.stream()
                .sorted(Comparator.comparing(Employe::getSalary).thenComparing(Employe::getAge))
                .map(Employe::getName)
                .forEach(System.out::println);

        // 自定义排序，先按工资排序再按年龄排序
        empList.stream()
                .sorted((p1, p2) -> {
                    if (p1.getSalary() == p2.getSalary())
                        return p1.getAge() - p2.getAge();
                    else
                        return p1.getSalary() - p2.getSalary();
                }).map(Employe::getName)
                .forEach(System.out::println);

    }
}
