package com.merrycodes.service.impl;

import com.merrycodes.constant.enums.RoleTypeEnum;
import com.merrycodes.model.entity.Role;
import com.merrycodes.model.entity.User;
import com.merrycodes.model.entity.UserRole;
import com.merrycodes.service.intf.InitApplicationService;
import com.merrycodes.service.intf.RoleService;
import com.merrycodes.service.intf.UserRoleService;
import com.merrycodes.service.intf.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 初始化应用service接口实现类
 *
 * @author MerryCodes
 * @date 2020/5/5 18:55
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class InitApplicationServiceImpl implements InitApplicationService {

    private final UserService userService;

    private final RoleService roleService;

    private final UserRoleService userRoleService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void initDataBase() {
        log.info(">>>>>>>>>>>>>>>>>>初始化数据库开始<<<<<<<<<<<<<<<<<<");
        for (RoleTypeEnum value : RoleTypeEnum.values()) {
            // 判断数据库中是否存在
            if (roleService.exits(value.getName(), value.getDescription())) {
                log.info(">>>>>>>>>>>>>>>>>>初始化角色 name={},descroption={} 开始<<<<<<<<<<<<<<<<<<", value.getName(), value.getDescription());
                roleService.save(Role.of(value.getName(), value.getDescription()));
                log.info(">>>>>>>>>>>>>>>>>>初始化角色结束<<<<<<<<<<<<<<<<<<");
            }
        }
        // 存在用户则不自动添加用户
        if (userService.count() == 0) {
            log.info(">>>>>>>>>>>>>>>>>>初始化用户表开始<<<<<<<<<<<<<<<<<<");
            User user = User.builder().enabled(true).username("123").password(bCryptPasswordEncoder.encode("123")).build();
            userService.save(user);
            log.info(">>>>>>>>>>>>>>>>>>初始化用户表结束<<<<<<<<<<<<<<<<<<");

            log.info(">>>>>>>>>>>>>>>>>>初始化用户角色表开始<<<<<<<<<<<<<<<<<<");
            Role role = roleService.findByName(RoleTypeEnum.ADMIN.getName());
            userRoleService.save(UserRole.of(user.getId(), role.getId()));
            log.info(">>>>>>>>>>>>>>>>>>初始化用户角色表结束<<<<<<<<<<<<<<<<<<");
        }
        log.info(">>>>>>>>>>>>>>>>>>初始化数据库结束<<<<<<<<<<<<<<<<<<");
    }
}
