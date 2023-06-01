package com.hfc.webflux.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by hfc on 2023/5/16.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    private String title;

    private int category;

    private double price;

}
