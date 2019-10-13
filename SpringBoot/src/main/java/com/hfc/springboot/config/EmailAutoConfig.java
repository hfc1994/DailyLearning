package com.hfc.springboot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * Created by hfc on 2019/10/10.
 */
@Configuration
public class EmailAutoConfig {
    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.port:25}")
    private Integer port;
    @Value("${mail.from}")
    private String from;
    @Value("${spring.mail.username}")
    private String user;
    @Value("${spring.mail.password}")
    private String pass;
    @Value("${mail.to}")
    private String to;


    @Bean
    public MailSender mailSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(this.host);
        sender.setPort(this.port);
        sender.setUsername(this.user);
        sender.setPassword(this.pass);
        sender.setDefaultEncoding("UTF-8");
        sender.setProtocol("smtp");
        return sender;
    }
}
