package com.merrycodes.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.merrycodes.constant.enums.RoleTypeEnum;
import com.merrycodes.model.entity.Role;
import com.merrycodes.model.form.query.RoleQueryForm;
import com.merrycodes.model.vo.RoleVo;
import com.merrycodes.service.intf.RoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertTrue;

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
        Role role = roleService.selectByName(RoleTypeEnum.ADMIN.getName());
        System.out.println(role);
    }

    @Test
    public void selectRoleByUserId() {
        List<Role> roles = roleService.selectRoleByUserId(1);
        roles.forEach(System.out::println);
    }

    @Test
    public void selectRoleListWithNameAndDescription() {
        List<Role> roles = roleService.selectRoleWithNameDescription();
        roles.forEach(System.out::println);
    }

    @Test
    public void selectRolePage() {
        RoleQueryForm roleQueryForm = new RoleQueryForm();
        roleQueryForm.setName("user");
        IPage<RoleVo> iPage = roleService.selectRolePage(1, 10, roleQueryForm);
        iPage.getRecords().forEach(System.out::println);
    }
}