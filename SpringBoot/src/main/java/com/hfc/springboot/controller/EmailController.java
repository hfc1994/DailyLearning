package com.hfc.springboot.controller;

import com.hfc.springboot.services.EmailServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hfc on 2019/10/10.
 */
@RestController
public class EmailController {

    @Autowired
    private EmailServices services;

    @GetMapping("/email/send")
    public boolean sendEmail() {
        return services.sendEmail();
    }
}
