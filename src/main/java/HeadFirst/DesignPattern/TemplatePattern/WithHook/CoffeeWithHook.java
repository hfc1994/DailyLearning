package HeadFirst.DesignPattern.TemplatePattern.WithHook;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by user-hfc on 2018/4/17.
 */
public class CoffeeWithHook extends CaffeineBeverageWithHook
{
    @Override
    void brew()
    {
        System.out.println("Dripping Coffee through filter");
    }

    @Override
    void addCondiments()
    {
        System.out.println("Adding Sugar and Milk");
    }

    @Override
    public boolean customerWantsCondiments()
    {
        String answer = getUserInput();

        if ("y".equals(answer.toLowerCase()))
            return true;
        else
            return false;
    }

    private String getUserInput() {
        String answer = null;

        System.out.println("Would you like milk and sugar with your coffee (y/n)?");

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try {
            answer = in.readLine();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (null == answer)
        {
            return "n";
        }

        return answer;
    }
}
