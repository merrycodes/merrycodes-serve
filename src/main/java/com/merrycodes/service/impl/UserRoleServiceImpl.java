package com.merrycodes.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.merrycodes.mapper.UserRoleMapper;
import com.merrycodes.model.entity.UserRole;
import com.merrycodes.service.intf.UserRoleService;
import org.springframework.stereotype.Repository;

/**
 * 用户角色service接口实现类
 *
 * @author MerryCodes
 * @date 2020/5/5 18:08
 */
@Repository
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
}
