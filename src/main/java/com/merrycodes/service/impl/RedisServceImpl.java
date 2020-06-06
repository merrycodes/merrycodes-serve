package com.merrycodes.service.impl;

import com.merrycodes.service.intf.RedisServce;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Duration;

/**
 * Redis缓存Service接口实现类
 *
 * @author MerryCodes
 * @date 2020/6/6 17:13
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RedisServceImpl implements RedisServce {

    private final RedisTemplate<String, Object> redisTemplate;

    private ValueOperations<String, Object> operations;

    @PostConstruct
    private void init() {
        operations = redisTemplate.opsForValue();
    }

    /**
     * 存储数据
     *
     * @param key         key值
     * @param value       存储数据
     * @param expiredTime 过期时间（分钟）
     */
    @Override
    public void putObject(String key, Object value, Long expiredTime) {
        operations.set(key, value, Duration.ofMinutes(expiredTime));
    }

    /**
     * 获取数据
     *
     * @param key key值
     * @return 存储的数据
     */
    @Override
    public Object getObject(String key) {
        return operations.get(key);
    }

    /**
     * 移除数据
     *
     * @param key key值
     */
    @Override
    public void removeObject(String key) {
        redisTemplate.delete(key);
    }

}
