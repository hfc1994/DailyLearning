package com.hfc.springboot.controller;

import com.hfc.springboot.annotation.JwtToken;
import com.hfc.springboot.event.MockInvokeEvent;
import com.hfc.springboot.utils.ApplicationContextHolder;
import com.hfc.springboot.utils.EventPublishHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.*;

/**
 * Created by hfc on 2020/4/29.
 */
@RestController
@RequestMapping("/test")
public class DemoController {

    @Autowired
    private ApplicationContextHolder ach;

    @Autowired
    private EventPublishHolder publisher;

    @Value("${spring.datasource.druid.url}")
    private String dbUrl;

    @Value("${spring.datasource.druid.username}")
    private String dbUser;

    @Value("${spring.datasource.druid.password}")
    private String dbPasswd;

    /**
     * druid-spring-boot-starter里的DruidDataSourceAutoConfigure做好了初始化
     */
    @Autowired
    private DataSource druidDataSourceWrapper;

    @GetMapping("/event")
    public String testEvent() {
        String ret = "--- begin test event ---";
        publisher.getPublisher().publishEvent(new MockInvokeEvent("invoke testEvent method"));
        return ret;
    }

    @RequestMapping("/holder")
    public void testHolder() {
        ApplicationContext ac = ach.getAc();
        System.out.println(ac);
    }

    @GetMapping("/ds")
    public void testDataSource() throws SQLException {
        Connection conn = druidDataSourceWrapper.getConnection();
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery("select * from item_list");
        while (rs.next())
            System.out.println(rs.getString("item"));
        rs.close();
        stat.close();
        // 手动归还连接
        conn.close();
    }

    @GetMapping("/jdbc")
    public void testJdbcType() throws SQLException {
        // 这种方式是传统jdbc的方式，而且首先匹配上mysql自己的driver，因而也没经过druid
        Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPasswd);
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery("select * from item_list");
        while (rs.next())
            System.out.println(rs.getString("item"));
        rs.close();
        stat.close();
        conn.close();
    }

    @GetMapping("exception")
    public String testException() {
        // 测试全局异常捕捉器
        int ret = 2/0;
        return "TestException";
    }

    @GetMapping("/token")
    public String testJwtToken(@JwtToken String token) {
        System.out.println("token = " + token);
        return token;
    }

    /**
     * 测试 @ExceptionHandler(NullPointerException.class)
     * 和 @ExceptionHandler(RuntimeException.class)
     */
    @GetMapping("/nullpointer")
    public void testNullPointer() {
        String str = null;
        System.out.println(str.length());
    }

}
