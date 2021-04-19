package com.hfc.springboot.controller;

import com.hfc.springboot.entity.Book;
import com.hfc.springboot.entity.ItemList;
import com.hfc.springboot.mapper.BookMapper;
import com.hfc.springboot.mapper.ItemListMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by hfc on 2021/4/19.
 */
@RequestMapping("/mybatis")
@RestController
public class MyBatisController {

    @Resource
    private ItemListMapper itemListMapper;

    @Resource
    private BookMapper bookMapper;

    @GetMapping("/itemlist/{id}")
    public ItemList getItemListById(@PathVariable Integer id) {
        return itemListMapper.queryById(id);
    }

    @GetMapping("/book/{id}")
    public Book getBookById(@PathVariable Integer id) {
        return bookMapper.queryById(id);
    }
}
