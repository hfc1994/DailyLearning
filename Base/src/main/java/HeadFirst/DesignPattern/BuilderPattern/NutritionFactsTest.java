package HeadFirst.DesignPattern.BuilderPattern;

/**
 * Created by user-hfc on 2017/9/10.
 */
public class NutritionFactsTest
{
    private int servingSize;
    private int servings;
    private int calories;
    private int fat;
    private int sodium;
    private int carbohydrate;;

    private NutritionFactsTest(){}

    public NutritionFactsTest servingSize(int val)
    {
        servingSize = val;
        return this;
    }

    public NutritionFactsTest servings(int val)
    {
        servings = val;
        return this;
    }

    public NutritionFactsTest calories(int val)
    {
        calories = val;
        return this;
    }

    public NutritionFactsTest fat(int val)
    {
        fat = val;
        return this;
    }

    public NutritionFactsTest  carbohydrate(int val)
    {
        carbohydrate = val;
        return this;
    }

    public NutritionFactsTest sodium(int val)
    {
        sodium = val;
        return this;
    }

    public NutritionFactsTest builder()
    {
        return new NutritionFactsTest();
    }


    public static void main(String[] args)
    {
        NutritionFactsTest nt = new NutritionFactsTest();
        nt.calories(2).fat(3)
                .carbohydrate(4).sodium(5).servingSize(6).servings(7).builder();
        System.out.println(nt.servings);
        System.out.println(nt.servingSize);
        System.out.println(nt.calories);
        System.out.println(nt.fat);
        System.out.println(nt.sodium);
        System.out.println(nt.carbohydrate);
    }
}
