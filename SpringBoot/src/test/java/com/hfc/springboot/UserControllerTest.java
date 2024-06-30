package com.hfc.springboot;

import com.hfc.springboot.controller.UserController;
import com.hfc.springboot.entity.User;
import com.hfc.springboot.services.UserService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Created by hfc on 2024/6/30.
 */

// 单元测试，不会真实调用接口
// SpringRunner 是 SpringJUnit4ClassRunner 的简写版本，两者等价
@RunWith(SpringRunner.class)
// 不会初始化整个 Spring 环境，仅仅只会初始化 UserController 等相关的 Bean
@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Mock 后不会执行真正的后端逻辑，而是走 Mock 的逻辑
    // 这里实际注入的是一个使用 Mockito 创建的 UserService Mock 代理对象
    @MockBean
    private UserService userService;

    private static final Long USER_ID = 9527L;

    @Test
    public void testQueryById() throws Exception {
        System.out.println("before mock: " + userService.queryById(USER_ID));
        Mockito.when(userService.queryById(USER_ID))
                .thenReturn(User.builder()
                        .id(USER_ID)
                        .name("lisi")
                        .age(20)
                        .gender("male")
                        .address("yyyyyyy").build());
        System.out.println("after mock: " + userService.queryById(USER_ID));

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/user/id/" + USER_ID));
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("lisi"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(20));

        resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/user/id/12345"));
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(""))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.emptyOrNullString()));
    }

}
