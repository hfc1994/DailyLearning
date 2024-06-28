package com.hfc.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

// 这个注解表示使用 SpringRunner 来运行测试类，它集成了 JUnit 和 Spring TestContext 框架，
// 可以在测试中方便地加载 Spring 应用上下文
@RunWith(SpringRunner.class)
// 这个注解表示 SpringBoot 应用上下文应该加载 Application 类中定义的配置
// 它会启动整个 Spring 应用上下文，类似于正常运行时的配置
@SpringBootTest(classes = Application.class)
// 这个注解自动配置 MockMvc，用于模拟 HTTP 请求和响应，而无需启动整个服务器
@AutoConfigureMockMvc
public class TestMyBatisController {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnBookDetails() throws Exception {
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/mybatis/book/100000"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.oid").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.scores").value(8.1))
                .andDo(MockMvcResultHandlers.print());

        System.out.println("with interceptor: " + (actions.andReturn().getInterceptors() == null));
    }

}
