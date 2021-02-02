package com.chenyi.redisson;

import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

public class RedissonService {

    public RedissonClient redissonClient;

    public RedissonService(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    public String getString(String key) throws Exception {
        String value = (String)this.redissonClient.getBucket(key).get();
        return value;
    }

    public Object getObject(String key) throws Exception {
        Object o = this.redissonClient.getBucket(key).get();
        return o;
    }

    public void setStringTime(String key, String value, long sec) {
        this.redissonClient.getBucket(key).set(value, sec, TimeUnit.SECONDS);
    }

    public void setObjectTime(String key, Object value, long sec) {
        this.redissonClient.getBucket(key).set(value, sec, TimeUnit.SECONDS);
    }

    public void setObject(String key, String value) {
        this.redissonClient.getBucket(key).set(value);
    }

    public boolean remove(String key) {
        boolean delete = this.redissonClient.getBucket(key).delete();
        return delete;
    }

    public void setObjectSerTime(Object key, String value, long sec) {
    }

}
