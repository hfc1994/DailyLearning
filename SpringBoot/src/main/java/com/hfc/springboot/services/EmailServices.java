package com.hfc.springboot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

/**
 * Created by hfc on 2019/10/10.
 */
@Service
public class EmailServices {

    @Value("${mail.from}")
    private String from;
    @Value("${mail.to}")
    private String to;
    @Autowired(required = false)
    private MailSender sender;

    public boolean sendEmail() {
        if (sender == null) {
            System.out.println("fail to send!");
            return false;
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(this.from);
        message.setTo(this.to);
        message.setSubject("这应该是个测试的标题");
        message.setText("这个就是测试的内容");

        try {
            sender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
