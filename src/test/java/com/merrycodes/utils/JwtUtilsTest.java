package com.merrycodes.utils;

import com.merrycodes.config.JwtConfig;
import com.merrycodes.model.entity.JwtPayload;
import com.merrycodes.model.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * @author MerryCodes
 * @date 2020/5/10 13:10
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class JwtUtilsTest {

    public JwtConfig jwtConfig;

    @Autowired
    public void setJwtConfig(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @Test
    public void generateToken() {
        User admin = User.builder().username("admin").enabled(true).build();
        System.out.println(JwtUtils.generateToken(jwtConfig.getJwtPayloadUserKey(), jwtConfig.getPrivateKey(), admin, jwtConfig.getExpiration()));
    }

    @Test
    public void getUserFromToken() {
        String token = "eyJ0eXBlIjoiSldUIiwiYWxnIjoiUlMyNTYifQ.eyJqdGkiOiJNRFU1TUdOak9XUXROalV3TXkwMFlqSmpMVGc1WVdVdFl6TmpNemRrTjJWaE1qY3oiLCJ1c2VyIjoie1widXNlcm5hbWVcIjpcImFkbWluXCIsXCJlbmFibGVkXCI6dHJ1ZX0iLCJleHAiOjE1ODkxODI1NjksInN1YiI6ImFkbWluIiwiaWF0IjoxNTg5MDk2MTY5fQ.TFycjEx-aPnBd_FM2tit7dM1-V4PAMXOtWkRBu_5xe7sdgFhlHuZNB4mphTGCVjHtblb25p0DepUZrnYOCMT5ZVMOQPKS9kDTgcTpz8jWv1US7uJWecKIlz8e-p9UncnD2skGQdslPPXAKjT-z5FgjpeRRlafrbz0AtxKE5qDMMBBXoXZUBhcgKZuci-nwDvFE3gOE6c0LS_oZX930nWct8gnPu09NH5uQ9R-j60yje3HZyGWmYX7v-rn3RMDrlc2uiKttFt67pK6pygC_rZ4yOVmThysh1MoL8p81YSwJep7_A4HnYIbdcK4tiLl20s5rjy8GFuN2_VyctphXfkkg";
        JwtPayload jwtPayload = JwtUtils.getUserFromToken(token, jwtConfig.getJwtPayloadUserKey(), jwtConfig.getPublicKey());
        System.out.printf("id:%s\n", jwtPayload.getId());
        System.out.printf("user:%s\n", jwtPayload.getUser());
        System.out.printf("expire:%s\n", jwtPayload.getExpire());
        System.out.println(jwtPayload.isTokenExpired());
    }
}