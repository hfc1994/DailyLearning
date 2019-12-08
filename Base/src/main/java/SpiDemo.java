import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by hfc on 2019/12/8.
 */
public class SpiDemo {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/douban?useUnicode=true&characterEncoding=utf8&useSSL=false";
        try {
            PrintWriter writer = new PrintWriter(System.out);
            DriverManager.setLogWriter(writer);
            Connection conn = DriverManager.getConnection(url, "root", "123456");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("over");

    }
}
