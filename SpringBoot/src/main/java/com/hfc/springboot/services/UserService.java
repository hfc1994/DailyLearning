package com.hfc.springboot.services;

import com.hfc.springboot.entity.User;
import org.springframework.stereotype.Service;

/**
 * Created by hfc on 2024/6/30.
 */
@Service
public class UserService {

    public User queryById(Long id) {
        return User.builder()
                .id(id)
                .name("zhangsan")
                .age(18)
                .gender("male")
                .address("xxxxxxx")
                .build();
    }

}
