package com.hfc.springboot.model;

import com.hfc.springboot.annotation.InputEnum;
import com.hfc.springboot.constants.GenderEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Created by hfc on 2024/9/17.
 */
@Data
public class UserUpdateGenderDTO {

    @NotNull(message = "用户编号不能为空")
    private Integer id;

    @NotNull(message = "性别不能为空")
    @InputEnum(value = GenderEnum.class, message = "性别必须是 {value}")
    private Integer gender;

}
