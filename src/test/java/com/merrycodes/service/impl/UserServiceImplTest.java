package com.merrycodes.service.impl;

import com.merrycodes.config.JwtConfig;
import com.merrycodes.model.entity.User;
import com.merrycodes.service.intf.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author MerryCodes
 * @date 2020/5/6 11:48
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceImplTest {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Test
    public void loadUserByUsername() {
        UserDetails userDetails = userService.loadUserByUsername("123");
        System.out.println(userDetails);
        assertNotNull(userDetails);
    }
}