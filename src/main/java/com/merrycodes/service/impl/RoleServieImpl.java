package com.merrycodes.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.merrycodes.mapper.RoleMapper;
import com.merrycodes.model.entity.Role;
import com.merrycodes.model.entity.UserRole;
import com.merrycodes.model.form.query.RoleQueryForm;
import com.merrycodes.model.vo.RoleVo;
import com.merrycodes.service.intf.RoleService;
import com.merrycodes.service.intf.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.merrycodes.constant.consist.CacheValueConsist.CACHE_VALUE_ROLE;

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

    private UserRoleService userRoleService;

    @Lazy
    @Autowired
    public void setUserRoleService(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    /**
     * 角色分页查询 (分页)
     *
     * @param current       当前页数
     * @param size          当前分页总页数
     * @param roleQueryForm 角色查询表当类
     * @return 分页 Page 对象接口 {@link IPage}
     */
    @Override
    @Cacheable(cacheNames = CACHE_VALUE_ROLE, key = "'userList['+#current+':'+#size+':'+#roleQueryForm+']'")
    public IPage<RoleVo> selectRolePage(Integer current, Integer size, RoleQueryForm roleQueryForm) {
        String roleName = roleQueryForm == null ? null : roleQueryForm.getName();
        Page<RoleVo> page = new Page<>(current, size);
        LambdaQueryWrapper<Role> wrapper = Wrappers.<Role>lambdaQuery()
                .eq(StringUtils.isNotEmpty(roleName), Role::getName, roleName);
        return roleMapper.selectRolePageWithUserCount(page, wrapper);
    }

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
    public Role selectByName(String name) {
        LambdaQueryWrapper<Role> wrapper = Wrappers.<Role>lambdaQuery()
                // 查询出来的结果仅包括下面的字段
                .select(Role::getId).eq(Role::getName, name);
        return roleMapper.selectOne(wrapper);
    }

    /**
     * 根据用户id查询用户的角色
     *
     * @param id 用户id
     * @return 角色集合
     */
    @Override
    @Cacheable(cacheNames = CACHE_VALUE_ROLE, key = "'roleByUserId['+#id+']'")
    public List<Role> selectRoleByUserId(Integer id) {
        List<UserRole> userRoles = userRoleService.selectRoleIdByUserId(id);
        List<Integer> roleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        // 在用户没有角色的情况下，直接放回空的集合
        if (roleIds.size() == 0) {
            return Lists.newArrayList();
        }
        // 可以使用insql
        // inSql(Role::getId, String.format("SELECT role_id FROM user_role WHERE user_id = %d", id));
        // 但是想着不想出现sql所以 UserRoleService 写了一个获取id的方法，或者使用xml
        LambdaQueryWrapper<Role> wrapper = Wrappers.<Role>lambdaQuery()
                .select(Role::getId, Role::getName).in(roleIds.size() > 0, Role::getId, roleIds);
        return roleMapper.selectList(wrapper);
    }

    /**
     * 查询角色的名字和描述
     *
     * @return 角色集合
     */
    @Override
    @Cacheable(cacheNames = CACHE_VALUE_ROLE, key = "'roleWithNameDescription'")
    public List<Role> selectRoleWithNameDescription() {
        LambdaQueryWrapper<Role> wrapper = Wrappers.<Role>lambdaQuery()
                .select(Role::getId, Role::getName, Role::getDescription);
        return roleMapper.selectList(wrapper);
    }

}
