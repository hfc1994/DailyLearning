package com.hfc.springboot.model;

import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.AssertTrue;
import lombok.Data;

/**
 * Created by hfc on 2024/9/17.
 */
@Data
public class UserUpdateStatusDTO {

    public interface Group01 {}

    public interface Group02 {}

    @AssertTrue(message = "状态必须为 true", groups = Group01.class)
    @AssertFalse(message = "状态必须为 false", groups = Group02.class)
    private Boolean status;

}
