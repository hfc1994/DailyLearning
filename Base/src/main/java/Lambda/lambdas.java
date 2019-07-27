package Lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by user-hfc on 2018/3/26.
 */
public class lambdas
{
    public static void main(String[] args)
    {
        lambdas lambdas = new lambdas();
        //Lambda.lambdas.method1();
        //Lambda.lambdas.method2();
        lambdas.method3();

    }

    public void method1()
    {
        ArrayList<Integer> alist = new ArrayList<>();

        alist.add(27);
        alist.add(54);
        alist.add(12);
        alist.add(76);
        alist.add(66);

        long num = alist.stream().filter(list -> list > 30).count();

        boolean result1 = alist.stream().allMatch(list -> list > 30);
        boolean result2 = alist.stream().allMatch(list -> list > 3);
        boolean result3 = alist.stream().anyMatch(list -> list > 30);

        //惰性求值方法
        alist.stream().filter(list -> {
            System.out.println(list);
            return (list > 25);
        });

        System.out.println("-------");

        //及早求值方法
        long num1 = alist.stream().filter(list -> {
            System.out.println(list);
            return (list > 25);
        }).count();


        System.out.println(num);
        System.out.println("---" + num1);
        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);

        //----------------

        List<String> collected = Stream.of("a", "b", "hello")
                .map(String -> String.toUpperCase())
                .collect(Collectors.toList());
    }

    public void method2()
    {
        int result1 = Stream.of(1, 2, 3, 4).reduce(0, (acc, element) -> acc + element);
        System.out.println(result1);

        int result2 = Stream.of(1, 2, 3, 4, 5).reduce(0, (jian, element) -> jian - element);
        System.out.println(result2);
    }

    public void method3()
    {
        Optional<String> aa = Optional.of("a");
        Optional emptyOptional = Optional.empty();
        Optional alsoEmpty = Optional.ofNullable(null);

        System.out.println(emptyOptional.isPresent());
        System.out.println(alsoEmpty.isPresent());
        System.out.println(aa.isPresent());
    }
}
