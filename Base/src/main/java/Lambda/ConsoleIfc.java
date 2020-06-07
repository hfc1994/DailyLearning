package Lambda;

/**
 * Created by hfc on 2020/4/20.
 *
 *
 * 添加了@FunctionalInterface表明是一个“函数接口”
 * 因此这个接口里面只能有一个待实现的方法
 */
@FunctionalInterface
public interface ConsoleIfc {

    String consoleName = "BaseConsole";

    void println(String msg);

//    void println2(String msg);

}
