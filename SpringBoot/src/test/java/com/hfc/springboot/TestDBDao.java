package com.hfc.springboot;

import com.hfc.springboot.entity.Book;
import com.hfc.springboot.mapper.BookMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestDBDao {

    @Resource
    private BookMapper bookMapper;

    /**
     * 在运行测试用例之前与之后创建测试数据和清理测试数据
     */
    @Test
    @Sql(scripts = "/sql/initial.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void initEnv() {
        Book book = bookMapper.queryByTitle("小学语文");
        System.out.println("book id is: " + book.getId());
    }

}
