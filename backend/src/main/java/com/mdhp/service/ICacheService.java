package com.mdhp.service;

import java.util.concurrent.TimeUnit;

public interface ICacheService {

    void storeInCache(String key, Object value, long duration, TimeUnit unit);

    <T> T getFromCache(String key);

    void evictCache(String key);
}
