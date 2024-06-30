package com.hfc.springboot.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by hfc on 2024/6/30.
 */
@Setter
@Getter
@Builder
@ToString
public class User {

    private Long id;

    private String name;

    private Integer age;

    private String gender;

    private String address;

}
