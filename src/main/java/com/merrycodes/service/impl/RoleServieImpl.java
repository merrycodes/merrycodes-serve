package com.merrycodes.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.merrycodes.mapper.RoleMapper;
import com.merrycodes.model.entity.Role;
import com.merrycodes.service.intf.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 角色service决口实现类
 *
 * @author MerryCodes
 * @date 2020/5/5 18:05
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RoleServieImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    private final RoleMapper roleMapper;

    /**
     * 根据角色名称和描述判断是否存在角色
     *
     * @param name        角色名
     * @param description 角色描述
     * @return {@link Boolean} 存在 / 不存在 false / true
     */
    @Override
    public Boolean exits(String name, String description) {
        LambdaQueryWrapper<Role> wrapper = Wrappers.<Role>lambdaQuery()
                // 查询出来的结果仅包括下面的字段
                .eq(Role::getName, name).eq(Role::getDescription, description);
        return roleMapper.selectCount(wrapper) == 0;
    }

    /**
     * 根据角色名查询角色
     *
     * @param name 角色名
     * @return {@link Role}
     */
    @Override
    public Role findByName(String name) {
        LambdaQueryWrapper<Role> wrapper = Wrappers.<Role>lambdaQuery()
                // 查询出来的结果仅包括下面的字段
                .select(Role::getId).eq(Role::getName, name);
        return roleMapper.selectOne(wrapper);
    }

}
