package com.hfc.dubbo.provider.service;

import org.apache.dubbo.config.annotation.DubboService;
import com.hfc.dubbo.provider.service.ifc.AddressService;
import org.springframework.stereotype.Component;

/**
 * Created by hfc on 2021/3/10.
 */
@Component
//@DubboService    // 未注解，则不会被注册
public class AddressServiceImpl implements AddressService {

    @Override
    public String getAddress() {
        return "earth";
    }

}
