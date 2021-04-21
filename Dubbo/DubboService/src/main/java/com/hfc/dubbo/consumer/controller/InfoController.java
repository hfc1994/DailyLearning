package com.hfc.dubbo.consumer.controller;

import com.hfc.dubbo.provider.service.ifc.InfoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hfc on 2021/3/6.
 */
@RestController
@RequestMapping("/info")
public class InfoController {

    // version="*"表示不关心服务端的版本号，dubbo默认会在多个版本中随机调用
    // check表示要进行启动时检查，当检查到依赖的服务未启动时便会阻止Spring的初始化，默认为true
    @DubboReference(version = "1.0.0", check = true, timeout = 3000, retries = 3)
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
