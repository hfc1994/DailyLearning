package com.hfc.springboot.annotation;

import com.hfc.springboot.validator.InputEnumValidator;
import com.hfc.springboot.validator.IntArrayValue;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Created by hfc on 2024/7/17.
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {InputEnumValidator.class})
public @interface InputEnum {

    Class<? extends IntArrayValue> value();

    String message() default "必须在指定范围内：";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
