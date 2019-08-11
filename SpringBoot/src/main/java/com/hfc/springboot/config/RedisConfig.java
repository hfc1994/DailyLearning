package com.hfc.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by hfc on 2019/7/27.
 */
@Component
public class RedisConfig {

//    @Bean
//    public RedisConnectionFactory redisConnectionFactory() {
////        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
////        config.setHostName("localhost");
////        return new JedisConnectionFactory(config);
//        return new JedisConnectionFactory();
//    }

//    @Bean
//    public RedisTemplate redisTemplate(RedisConnectionFactory factory) {
//        RedisTemplate redisTemplate = new RedisTemplate();
//        redisTemplate.setConnectionFactory(factory);
//        return redisTemplate;
//    }
}
