package HeadFirst.DesignPattern.AdapterPattern;

import HeadFirst.DesignPattern.AdapterPattern.ifc.Duck;

/**
 * Created by user-hfc on 2018/4/17.
 */
public class DuckTestDrive
{
    public static void main(String[] args)
    {
        MallardDuck mallardDuck = new MallardDuck();

        WildTurkey turkey = new WildTurkey();
        Duck turkeyAdapter = new TurkeyAdapter(turkey);

        System.out.println("The Turkey says...");
        turkey.gobble();
        turkey.fly();

        System.out.println("\nThe Duck says...");
        testDuck(mallardDuck);

        System.out.println("\nThe TurkeyAdapter says...");
        testDuck(turkeyAdapter);
    }

    static void testDuck(Duck duck)
    {
        duck.quack();
        duck.fly();
    }
}
