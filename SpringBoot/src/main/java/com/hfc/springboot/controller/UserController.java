package com.hfc.springboot.controller;

import com.hfc.springboot.entity.User;
import com.hfc.springboot.model.UserDTO;
import com.hfc.springboot.model.UserUpdateGenderDTO;
import com.hfc.springboot.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hfc on 2024/6/30.
 */
@RestController
@RequestMapping("/user")
// 在类上使用 @Validated 注解，表示需要对所有接口都进行参数校验，那么 Spring Validation 就会使用 AOP 进行切面进行参数校验，
// 该切面的拦截器使用的是 MethodValidationInterceptor。
// 在需要对对象进行参数校验的地方就需要嵌套校验，因此需要额外添加 @Valid 注解。
// 对于 #queryUserById(id) 是在 MethodValidationInterceptor 拦截器内，校验到参数不正确就会抛出 ConstraintViolationException 异常。
// 对于 #addUser(userDTO)，因为 userDTO 是个 POJO 对象，所以会走 SpringMVC 的 DataBinder 机制，它会调用 DataBinder.validate(...)
// 方法进行校验，在校验不通过时抛出 BindException。（当前版本实际是 MethodArgumentNotValidException）
//
// 在 SpringMVC 中，默认使用 DefaultHandlerExceptionResolver 处理异常。
// 对于 BindException 异常，处理成 400 的状态码。
// 对于 ConstraintViolationException 异常，没有特殊处理，所以处理成 500 的状态码。
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/id/{id}")
    public User queryUserById(@PathVariable(name = "id")
                                  @Min(value = 1L, message = "编号必须大于0") Long id) {
        return userService.queryById(id);
    }

    @PutMapping("/add")
    public User addUser(@RequestBody @Valid UserDTO userDTO) {
        System.out.println("user name: " + userDTO.getUsername());
        return User.builder()
                .id(112233L)
                .name(userDTO.getUsername())
                .build();
    }

    @PostMapping("/update/gender")
    public Object updateUserGender(@RequestBody @Valid UserUpdateGenderDTO userGenderDTO) {
        return userGenderDTO;
    }

}
