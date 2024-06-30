package com.hfc.springboot.controller;

import com.hfc.springboot.entity.User;
import com.hfc.springboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hfc on 2024/6/30.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/id/{id}")
    public User queryUserById(@PathVariable(name = "id") Long id) {
        return userService.queryById(id);
    }

}
