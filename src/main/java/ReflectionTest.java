import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by user-hfc on 2017/12/24.
 */
public class ReflectionTest
{
    public static void main(String[] args)
    {

        String name;

        name = "java.util.Date";

        try
        {
            Class cl = Class.forName(name);
            Class supercl = cl.getSuperclass();
            String modifiers = Modifier.toString(cl.getModifiers());
            if (modifiers.length() > 0)
                System.out.print(modifiers + " ");

            System.out.print("class " + name);

            if (supercl != null && supercl != Object.class)
                System.out.print(" extends " + supercl.getName());

            System.out.print("\\n{\\n");
            printConstructors(cl);
            System.out.println();
            printMethods(cl);
            System.out.println();
            printField(cl);
            System.out.println("}");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static void printField(Class cl)
    {
        Field[] fields = cl.getDeclaredFields();

        for (Field f : fields)
        {
            Class Type = f.getType();
            String name = f.getName();
            System.out.print("  ");
            String modifiers = Modifier.toString(f.getModifiers());
            if (modifiers.length() > 0)
                System.out.print(modifiers + " ");
            System.out.print(Type.getName() + " " + name + ";");
        }
    }

    private static void printMethods(Class cl)
    {
        Method[] methods = cl.getDeclaredMethods();
        for (Method m : methods)
        {
            Class retType = m.getReturnType();
            String name = m.getName();

            System.out.print("   ");
            String modifiers = Modifier.toString(m.getModifiers());
            if (modifiers.length() > 0)
                System.out.print(modifiers + " ");
            System.out.print(retType.getName() + " " + name + "(");

            Class[] paramType = m.getParameterTypes();
            for (int j = 0; j < paramType.length; j++)
            {
                if (j > 0)
                    System.out.print(", ");
                System.out.print(paramType[j].getName());
            }
            System.out.print(");");
        }
    }

    private static void printConstructors(Class cl)
    {
        Constructor[] constructors = cl.getDeclaredConstructors();
        for (Constructor c : constructors)
        {
            String name = c.getName();
            System.out.print("  ");
            String modifiers = Modifier.toString(c.getModifiers());
            if (modifiers.length() > 0)
                System.out.print(modifiers + " ");
            System.out.print(name + "(");
            Class[] paramType = c.getParameterTypes();
            for (int j=0; j < paramType.length; j++)
            {
                if (j>0)
                    System.out.print(", ");
                System.out.print(paramType[j].getName());
            }
            System.out.print(");");
        }
    }
}
