package Lambda;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by user-hfc on 2018/11/30.
 *
 * @author user-hfc.
 */
public class Exercise {

    private List<Transaction> transactions = null;

    public void initData() {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        this.transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2011, 700),
                new Transaction(alan, 2012, 950));
    }

    /**
     * problems
     *
     * 找出2011年发生的所有交易，并按交易额排序
     *
     * 交易员在哪些不同的城市工作过
     *
     * 查找所有来自剑桥的交易员，并按姓名排序
     *
     * 返回所有交易员的姓名字符串，并按字母顺序排序
     *
     * 有没有交易员在米兰工作的？
     *
     * 打印生活在剑桥的交易员的所有交易额
     *
     * 所有交易中，最高的交易额是多少
     *
     * 找到交易额最小的交易
     */

    public static void main(String[] args) {
        Exercise exercise = new Exercise();
        exercise.initData();

//        exercise.dealWithProblem1();
//        exercise.dealWithProblem2();
//        exercise.dealWithProblem3();
        exercise.dealWithProblem4();
//        exercise.dealWithProblem5();
//        exercise.dealWithProblem6();
//        exercise.dealWithProblem7();
//        exercise.dealWithProblem8();
    }

    /**
     * 找出2011年发生的所有交易，并按交易额降序排序
     */
    private void dealWithProblem1() {
        List<Transaction> tsList = this.transactions.stream()
                .filter(ts -> ts.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction::getValue).reversed())
                .collect(Collectors.toList());
        this.printCollectionObject(tsList);
    }

    /**
     * 交易员在哪些不同的城市工作过
     */
    private void dealWithProblem2() {
        Set<String> cities = this.transactions.stream()
                .map(ts -> ts.getTrader().getCity())
                .collect(Collectors.toSet());
        this.printCollectionResult(cities);
    }

    /**
     * 查找所有来自剑桥的交易员，并按姓名排序
     */
    private void dealWithProblem3() {
        List<Trader> traders = this.transactions.stream()
                .map(Transaction::getTrader)
                .filter(ts -> "Cambridge".equals(ts.getCity()))
                .collect(Collectors.toSet())
                .stream()
                .sorted(Comparator.comparing(Trader::getName))
                .collect(Collectors.toList());
        this.printCollectionObject(traders);

        //  以下注释是参考答案的内容
//        List<Trader> traders = this.transactions.stream()
//                .map(Transaction::getTrader)
//                .filter(ts -> "Cambridge".equals(ts.getCity()))
//                .distinct()
//                .sorted(Comparator.comparing(Trader::getName))
//                .collect(Collectors.toList());
//        this.printCollectionObject(traders);
    }

    /**
     * 返回所有交易员的姓名字符串，并按字母顺序排序
     */
    private void dealWithProblem4() {
        List<String> names = this.transactions.stream()
                .map(ts -> ts.getTrader().getName())
                .collect(Collectors.toSet())
                .stream()
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
        this.printCollectionResult(names);

        //  以下注释是参考答案的内容
//        String names = this.transactions.stream()
//                .map(ts -> ts.getTrader().getName())
//                .distinct()
//                .sorted()
//                .reduce("", (acc, ele) -> acc +ele + " ");
//        this.printResult(names);
    }

    /**
     * 有没有交易员在米兰工作的？
     */
    private void dealWithProblem5() {
        boolean hasAnyOneWorkedInMiLan = this.transactions.stream()
                .anyMatch(ts -> "Milan".equals(ts.getTrader().getCity()));
        this.printResult("the result of has any one worked in Milan is = " + hasAnyOneWorkedInMiLan);
    }

    /**
     * 打印生活在剑桥的交易员的所有交易额
     */
    private void dealWithProblem6() {
        System.out.println("---开始打印结果---");
        this.transactions.stream()
                .filter(ts -> "Cambridge".equals(ts.getTrader().getCity()))
                .collect(Collectors.groupingBy(ts -> ts.getTrader().getName()))
                .forEach((name, tss) -> {
                    System.out.println("trader name = " + name);
                    tss.forEach(ts -> System.out.println(ts.getYear() + " --- " + ts.getValue()));
                });
        System.out.println("---结果打印结束---");
    }

    /**
     * 所有交易中，最高的交易额是多少
     */
    private void dealWithProblem7() {
        System.out.println("---开始打印结果---");
        OptionalInt trade = this.transactions.stream()
                .mapToInt(Transaction::getValue)
                .max();
        System.out.println("the highest trade income = " + trade.getAsInt());
        System.out.println("---结果打印结束---");
    }

    /**
     * 找到交易额最小的交易
     */
    private void dealWithProblem8() {
        Optional<Transaction> ts = this.transactions.stream()
                .min(Comparator.comparing(Transaction::getValue));
        this.printCollectionObject(Arrays.asList(ts.get()));
    }

    private void printCollectionObject(Collection<?> objects) {
        System.out.println("---开始打印结果---");
        objects.forEach(System.out::println);
        System.out.println("---结果打印结束---");
    }

    private void printCollectionResult(Collection<String> content) {
        System.out.println("---开始打印结果---");
        content.forEach(System.out::println);
        System.out.println("---结果打印结束---");
    }

    private void printResult(String str) {
        System.out.println("---开始打印结果---");
        System.out.println(str);
        System.out.println("---结果打印结束---");
    }
}
