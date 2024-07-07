package com.hfc.springboot.controller;

import com.hfc.springboot.entity.Book;
import com.hfc.springboot.entity.ItemList;
import com.hfc.springboot.mapper.BookMapper;
import com.hfc.springboot.mapper.ItemListMapper;
import com.hfc.springboot.model.BookDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "/book/{id}",
            // 针对 Content-Type 请求头，请求头里必须有如下其一的类型
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            // 针对 Accept 请求头，按照 Accept 的类型返回数据
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Book getBookById(@PathVariable Integer id) {
        return bookMapper.queryById(id);
    }

    @GetMapping("/book")
    public Book queryBook(@RequestParam(name = "id") Integer id) {
        return bookMapper.queryById(id);
    }

    @PostMapping("/book")
    public Book queryBook(@RequestBody BookDTO bookDTO) {
        if (bookDTO.getId() == null && bookDTO.getTitle() == null) {
            throw new RuntimeException("illegal param");
        }

        if (bookDTO.getId() == null) {
            return bookMapper.queryByTitle(bookDTO.getTitle());
        } else {
            return bookMapper.queryById(bookDTO.getId());
        }
    }
}
