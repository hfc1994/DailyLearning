package com.hfc.springboot.controller;

import com.hfc.springboot.event.MockInvokeEvent;
import com.hfc.springboot.utils.ApplicationContextHolder;
import com.hfc.springboot.utils.EventPublishHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
        Connection conn = druidDataSourceWrapper.getConnection("root", "123456");
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery("select * from item_list");
        while (rs.next())
            System.out.println(rs.getString("item"));
        // 手动归还连接
        conn.close();
    }

    @GetMapping("exception")
    public String testException() {
        // 测试全局异常捕捉器
        int ret = 2/0;
        return "TestException";
    }

}
