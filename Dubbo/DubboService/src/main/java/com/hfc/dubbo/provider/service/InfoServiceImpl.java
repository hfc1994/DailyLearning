package com.hfc.dubbo.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.hfc.dubbo.provider.service.ifc.InfoService;
import org.springframework.stereotype.Component;

/**
 * Created by hfc on 2021/3/6.
 */
@Component
@Service    // dubbo的，用于暴露服务
public class InfoServiceImpl implements InfoService {

    @Override
    public String getInfo() {
        return "there is some information";
    }

}
