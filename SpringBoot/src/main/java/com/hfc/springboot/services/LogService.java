package com.hfc.springboot.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * Created by hfc on 2022/9/18.
 */
@Component
public class LogService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogService.class);

    @PostConstruct
    public void init() {
        LOGGER.debug("this is a debug level info");
        LOGGER.info("this is a info level msg");
        LOGGER.warn("this is a warn level msg");
        LOGGER.error("this is a error level msg");
        try {
            int i = 1 / 0;
        } catch (Exception e) {
            LOGGER.error("something error: " + e.getMessage());
//            LOGGER.error("something error: " + e.getMessage(), e);
        }

        int times = 20;
        for (int i=0; i<times; i++) {
            LOGGER.debug("this is time of " + i);
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }

}
