package com.hfc.dubbo.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hfc.dubbo.provider.service.ifc.InfoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hfc on 2021/3/6.
 */
@RestController
@RequestMapping("/info")
public class InfoController {

    @Reference
    private InfoService infoService;

    @RequestMapping("/print")
    public void getInfo() {
        try {
            System.out.println(infoService.getInfo());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
