package com.hfc.springboot.constants;

import com.hfc.springboot.validator.IntArrayValue;
import lombok.Getter;

import java.util.Arrays;

/**
 * Created by hfc on 2024/7/17.
 */
@Getter
public enum GenderEnum implements IntArrayValue {

    MALE(1, "男"),
    FEMALE(2, "女")
    ;

    private final Integer tag;

    private final String description;

    private static final int[] GenderArrays = Arrays.stream(values())
                    .mapToInt(GenderEnum::getTag)
                            .toArray();

    GenderEnum(Integer tag, String description) {
        this.tag = tag;
        this.description = description;
    }

    @Override
    public int[] array() {
        return GenderArrays;
    }

}
