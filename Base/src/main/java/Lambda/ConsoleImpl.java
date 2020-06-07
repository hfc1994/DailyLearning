package Lambda;

/**
 * Created by hfc on 2020/4/20.
 */
public class ConsoleImpl {

    private String consoleName = "StupidConsole";

    public static void main(String[] args) {
        ConsoleImpl impl = new ConsoleImpl();
        impl.createConsole().println("balabala");
    }

    /**
     * this::printMsg表明是把printMsg传递给ConsoleIfc唯一待实现的println函数
     * 该部分可以写成 (msg) -> printMsg(msg)
     * 该情况下printMsg()所在作用域是ConsoleImpl的实例里，所以可以使用该实例的变量。
     */
    public ConsoleIfc createConsole() {
        return this::printMsg;
//        return (msg) -> printMsg(msg);
    }

    public String printMsg(String msg) {
        String result = "This is " + this.consoleName + " - " + msg;
        System.out.println(result);
        return result;
    }
}
