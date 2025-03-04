package com.mdhp.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


@Service
public class CacheService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void storeInCache(String key, Object value, long duration, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, duration, unit);
    }


    @SuppressWarnings("unchecked")
    public <T> T getFromCache(String key) {
        return (T) redisTemplate.opsForValue().get(key);
    }

    public void evictCache(String key) {
        redisTemplate.delete(key);
    }
}
