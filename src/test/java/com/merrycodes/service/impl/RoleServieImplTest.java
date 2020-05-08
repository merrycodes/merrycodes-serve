package com.merrycodes.service.impl;

import com.merrycodes.constant.enums.RoleTypeEnum;
import com.merrycodes.model.entity.Role;
import com.merrycodes.service.intf.RoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author MerryCodes
 * @date 2020/5/5 20:14
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RoleServieImplTest {

    private RoleService roleService;

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Test
    public void exits() {
        Boolean exits = roleService.exits("admin", "asdf");
        assertTrue(exits);

    }

    @Test
    public void findByName() {
        System.out.println(RoleTypeEnum.ADMIN.getName());
        Role role = roleService.findByName(RoleTypeEnum.ADMIN.getName());
        System.out.println(role);
    }
}