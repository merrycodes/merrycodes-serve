package com.merrycodes.service.impl;

import com.merrycodes.service.intf.RedisServce;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.merrycodes.constant.consist.CacheValueConsist.CACHE_VALUE_TOKEN;

/**
 * @author MerryCodes
 * @date 2020/6/6 19:31
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisServceImplTest {

    private RedisServce redisServce;

    @Autowired
    public void setRedisServce(RedisServce redisServce) {
        this.redisServce = redisServce;
    }

    @Test
    public void putObject() {
        redisServce.putObject(CACHE_VALUE_TOKEN + "Test", "Token", 1L);
    }

    @Test
    public void getObject() {
        System.out.println(redisServce.getObject(CACHE_VALUE_TOKEN + "Test"));
    }

    @Test
    public void removeObject() {
        redisServce.removeObject(CACHE_VALUE_TOKEN + "Test");
    }
}