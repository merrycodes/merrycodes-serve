package com.merrycodes.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.merrycodes.model.entity.User;
import com.merrycodes.model.form.UserForm;
import com.merrycodes.model.form.query.UserQueryForm;
import com.merrycodes.service.intf.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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

    @Test
    public void selectUserPage() {
        UserQueryForm userQueryForm = new UserQueryForm();
        Map<String, String> sortMap = new HashMap<>(1);
        sortMap.put("name", "lastLogin");
        sortMap.put("sort", "asc");
        userQueryForm.setSort(sortMap);
        IPage<User> iPage = userService.selectUserPage(1, 10, userQueryForm);
        iPage.getRecords().forEach(System.out::println);
    }

    @Test
    public void updateUserEnable() {
        UserForm userForm = new UserForm();
        userForm.setId(5);
        userForm.setEnabled(true);
        userService.updateUserEnable(userForm);
    }

    @Test
    public void updateUserRole() {
        userService.updateUserRole(5, Collections.emptyList());

    }
}