package com.hfc.springboot.controller;

import com.hfc.springboot.utils.mq.sender.KafkaSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hfc on 2022/5/15.
 */
@RestController
@RequestMapping("/kafka")
public class KafkaController {

    @Autowired
    private KafkaSender kafkaSender;

    @GetMapping("/send/{msg}")
    public void testSend(@PathVariable("msg") String msg) {
        kafkaSender.send(msg);
    }

}
