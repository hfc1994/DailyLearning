package com.hfc.springboot.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * Created by hfc on 2024/7/14.
 */
@Data
public class UserDTO {

    private Long id;

    @NotEmpty(message = "用户名不能为空")
    @Length(min = 5, max = 16, message = "用户名长度为 5-16")
    @Pattern(regexp = "^[A-Za-z0-9]+$]", message = "用户名只能是字母和数字")
    private String username;

    @NotEmpty(message = "密码不能为空")
    @Length(min = 8, max = 20, message = "密码长度为 8-20")
    private String password;

}
