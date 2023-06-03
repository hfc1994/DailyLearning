package com.hfc.webflux.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Created by hfc on 2023/6/2.
 */
@Data
@Table("test")
public class Person {

    @Id
    private Long id;

    private String name;

}
