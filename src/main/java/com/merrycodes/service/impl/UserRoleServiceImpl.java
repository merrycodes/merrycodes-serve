package com.merrycodes.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.merrycodes.constant.enums.RoleTypeEnum;
import com.merrycodes.mapper.UserRoleMapper;
import com.merrycodes.model.entity.Role;
import com.merrycodes.model.entity.UserRole;
import com.merrycodes.service.intf.RoleService;
import com.merrycodes.service.intf.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.merrycodes.constant.consist.CacheValueConsist.CACHE_VALUE_USER_ROLE;

/**
 * 用户角色service接口实现类
 *
 * @author MerryCodes
 * @date 2020/5/5 18:08
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    private final UserRoleMapper userRoleMapper;

    private RoleService roleService;

    @Lazy
    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * 通过用户id查询角色id
     *
     * @param id 用户id
     * @return 用户角色关系类集合
     */
    @Override
    @Cacheable(cacheNames = CACHE_VALUE_USER_ROLE, key = "'roleIdByUserId['+#id+']'")
    public List<UserRole> selectRoleIdByUserId(Integer id) {
        LambdaQueryWrapper<UserRole> wrapper = Wrappers.<UserRole>lambdaQuery()
                .select(UserRole::getRoleId).eq(UserRole::getUserId, id);
        return userRoleMapper.selectList(wrapper);
    }

    /**
     * 通过用户id删除用户角色关联数据
     *
     * @param id 用户id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = CACHE_VALUE_USER_ROLE, beforeInvocation = true, allEntries = true)
    public void deleteByUserId(Integer id) {
        LambdaQueryWrapper<UserRole> wrapper = Wrappers.<UserRole>lambdaQuery()
                .eq(UserRole::getUserId, id);
        userRoleMapper.delete(wrapper);
    }

    /**
     * 插入用户角色
     *
     * @param userId 用户id
     * @param roleId 角色id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertOn(Integer userId, Integer roleId) {
        userRoleMapper.insert(UserRole.of(userId, roleId));
    }

    /**
     * 批量插入用户角色
     *
     * @param userId  用户id
     * @param roleIds 角色id数组
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertBatch(Integer userId, List<Integer> roleIds) {
        roleIds.forEach(roleId -> insertOn(userId, roleId));
    }

    /**
     * 判断用户是否有 ADMIN 角色
     *
     * @param userId 用户id
     * @return {@link Boolean}
     */
    @Override
    public Boolean checkAdminRole(Integer userId) {
        List<Role> roles = roleService.selectRoleByUserId(userId);
        List<Role> adminRole = roles.stream().filter(item -> StringUtils.equals(item.getName(), RoleTypeEnum.ADMIN.getName())).collect(Collectors.toList());
        return adminRole.size() > 0;
    }

}
