package HeadFirst.DesignPattern.BuilderPattern;

/**
 * Created by user-hfc on 2017/9/10.
 */

//Builder Pattern
public class NutritionFacts
{
    private final int servingSize;
    private final int servings;
    private final int calories;
    private final int fat;
    private final int sodium;
    private final int carbohydrate;

    public static class Builder
    {
        //必需的参数
        private final int servingSize;
        private final int servings;

        //可选参数
        private int calories = 0;
        private int fat = 0;
        private int sodium = 0;
        private int carbohydrate = 0;

        public Builder(int servingSize, int servings)
        {
            this.servingSize = servingSize;
            this.servings = servings;
        }

        public Builder calories(int val)
        {
            calories = val;
            return this;
        }

        public Builder fat(int val)
        {
            fat = val;
            return this;
        }

        public Builder carbohydrate(int val)
        {
            carbohydrate = val;
            return this;
        }

        public Builder sodium(int val)
        {
            sodium = val;
            return this;
        }

        public NutritionFacts build()
        {
            return new NutritionFacts(this);
        }
    }

    private NutritionFacts(Builder builder)
    {
        servingSize = builder.servingSize;
        servings = builder.servings;
        calories = builder.calories;
        fat = builder.fat;
        sodium = builder.sodium;
        carbohydrate = builder.carbohydrate;
    }

    public static void main(String[] args)
    {
        NutritionFacts cocaCola = new NutritionFacts.Builder(250,9)
                .calories(100).sodium(12).carbohydrate(34).fat(10).build();

    }
}









