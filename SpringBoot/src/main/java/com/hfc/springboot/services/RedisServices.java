package com.hfc.springboot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hfc on 2019/7/27.
 */
@Service
public class RedisServices {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void insertStr(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    public String getStr(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    public String getStrWithException(String key) {
        // 为了LogAspects测试AfterThrowing用的
        int ret = 2/0;
        return stringRedisTemplate.opsForValue().get(key);
    }

    // 获取keys中还有效的key的个数
    public Long getCountOfStr(List<String> keys) {
        return stringRedisTemplate.countExistingKeys(keys);
    }

    public void insertList(String key, List<String> content) {
        redisTemplate.opsForList().rightPushAll(key, content);
    }

    public String getOneFromList(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    public List<String> getAllFromList(String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }
}
