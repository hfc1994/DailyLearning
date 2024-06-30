package com.hfc.springboot;

import com.alibaba.fastjson.JSON;
import com.hfc.springboot.model.BookDTO;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
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

// 集成测试，真实调用接口
// 这个注解表示使用 SpringRunner 来运行测试类，它集成了 JUnit 和 Spring TestContext 框架，
// 可以在测试中方便地加载 Spring 应用上下文
@RunWith(SpringRunner.class)
// 这个注解表示 SpringBoot 应用上下文应该加载 Application 类中定义的配置
// 它会启动整个 Spring 应用上下文，类似于正常运行时的配置
@SpringBootTest(classes = Application.class)
// 这个注解自动配置 MockMvc，用于模拟 HTTP 请求和响应，而无需启动整个服务器
@AutoConfigureMockMvc
public class MyBatisControllerTest {

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

    @Test
    public void shouldReturnNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/mybatis/book/detail/100000"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testGetMethodWithParam() throws Exception {
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/mybatis/book")
                .param("id", "100000"));
        actions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.scores").value(8.1));

        mockMvc.perform(MockMvcRequestBuilders.get("/mybatis/book")
                        .param("title", "Wolfheart"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(new StringMatcher("Required request parameter 'id' for method")));

        mockMvc.perform(MockMvcRequestBuilders.get("/mybatis/book")
                        .param("id", "100000")
                        .param("title", "Wolfheart"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.scores").value(8.1));
    }

    @Test
    public void testPostMethod() throws Exception {
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.post("/mybatis/book"));
        actions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string(new StringMatcher("Required request body is missing")));

        actions = mockMvc.perform(MockMvcRequestBuilders.post("/mybatis/book"));
        actions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string(new StringMatcher("Required request body is missing")));

        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(100000);
        mockMvc.perform(MockMvcRequestBuilders.post("/mybatis/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(bookDTO)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.scores").value(8.1));

        bookDTO = new BookDTO();
        bookDTO.setTitle("Wolfheart");
        mockMvc.perform(MockMvcRequestBuilders.post("/mybatis/book")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JSON.toJSONString(bookDTO)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.oid").value(53763));

        bookDTO.setId(100000);
        mockMvc.perform(MockMvcRequestBuilders.post("/mybatis/book")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JSON.toJSONString(bookDTO)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.scores").value(8.1));
    }

    private static class StringMatcher extends BaseMatcher<String> {

        private final String targetValue;

        public StringMatcher(String value) {
            this.targetValue = value;
        }

        @Override
        public boolean matches(Object o) {
            if (!(o instanceof String)) {
                return false;
            }
            String str = (String) o;
            return str.contains(targetValue);
        }

        @Override
        public void describeTo(Description description) {
            description.appendText(targetValue);
        }
    }
}
