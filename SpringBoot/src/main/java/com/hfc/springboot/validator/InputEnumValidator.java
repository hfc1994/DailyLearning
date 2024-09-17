package com.hfc.springboot.validator;

import com.hfc.springboot.annotation.InputEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by hfc on 2024/7/17.
 */
public class InputEnumValidator implements ConstraintValidator<InputEnum, Integer> {

    private Set<Integer> values;

    @Override
    public void initialize(InputEnum inputEnumAnno) {
        IntArrayValue[] intArray = inputEnumAnno.value().getEnumConstants();
        if (intArray == null || intArray.length == 0) {
            this.values = Collections.emptySet();
        } else {
            this.values = Arrays.stream(intArray[0].array())
                    .boxed()
                    .collect(Collectors.toSet());
        }

        ConstraintValidator.super.initialize(inputEnumAnno);
    }

    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext context) {
        if (this.values.contains(integer)) {
            return true;
        }

        context.disableDefaultConstraintViolation();    // 禁用默认的 message
        // 重新添加错误提示
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()
                .replaceAll("\\{value}", values.toString())).addConstraintViolation();
        return false;
    }

}
