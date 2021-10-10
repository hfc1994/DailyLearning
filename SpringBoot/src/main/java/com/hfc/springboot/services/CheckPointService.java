package com.hfc.springboot.services;

import com.hfc.springboot.annotation.CheckPoint;
import org.springframework.stereotype.Service;

/**
 * Created by hfc on 2021/10/10.
 *
 * @CheckPoint的测试类
 *
 */
@Service
public class CheckPointService {

    @CheckPoint(hint = "no hint")
    public String doSomething1(int args1, int args2, String args3) {
        return args3 + args1 + args2;
    }

    @CheckPoint(hint = "no hint")
    public String doSomething2(int args1, String args2, String extInfo) {
        return extInfo + args2 + args1;
    }

}
