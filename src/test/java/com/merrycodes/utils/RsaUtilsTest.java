package com.merrycodes.utils;

import com.merrycodes.config.JwtConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author MerryCodes
 * @date 2020/5/10 13:10
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RsaUtilsTest {

    private JwtConfig jwtConfig;

    @Autowired
    public void setJwtConfig(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @Test
    public void getPublicKey() throws Exception {
        System.out.println(RsaUtils.getPublicKey(jwtConfig.getPublicKeyPath()));
    }

    @Test
    public void getPrivateKey() throws Exception {
        System.out.println(RsaUtils.getPrivateKey(jwtConfig.getPrivateKeyPath()));
    }
}