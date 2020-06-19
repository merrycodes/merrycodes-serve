package com.merrycodes.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.merrycodes.mapper.UserMapper;
import com.merrycodes.model.entity.Role;
import com.merrycodes.model.entity.User;
import com.merrycodes.model.form.ChangePasswordForm;
import com.merrycodes.model.form.UserForm;
import com.merrycodes.model.form.query.UserQueryForm;
import com.merrycodes.service.intf.RedisServce;
import com.merrycodes.service.intf.UserRoleService;
import com.merrycodes.service.intf.UserService;
import com.merrycodes.utils.CurrentUserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.merrycodes.constant.consist.CacheValueConsist.*;
import static com.merrycodes.constant.consist.SortMapConsist.*;

/**
 * 用户service实现类接口
 *
 * @author MerryCodes
 * @date 2020/5/4 18:22
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserMapper userMapper;

    private final RedisServce redisServce;

    private final UserRoleService userRoleService;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 解决循环依赖
     */
    @Lazy
    @Autowired
    public void setbCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * Spring Security 用户登录验证的方法
     *
     * @param username 用户名
     * @return {@link UserDetails}
     * @throws UsernameNotFoundException 用户名不存在异常
     */
    @Override
    @Cacheable(cacheNames = CACHE_VALUE_USER, key = "'username['+#username+']'", unless = "#result == null")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectBynameWithRole(username);
        if (user == null) {
            throw new UsernameNotFoundException("");
        }
        return user;
    }

    /**
     * 用户分页查询
     * 使用 orderByDesc / orderByDesc 编译器会有警告 使用注解抹去
     *
     * @param current       当前页数
     * @param size          当前分页总页数
     * @param userQueryForm 用户查询表单类
     * @return 分页 Page 对象接口 {@link IPage}
     * @see <a href="https://github.com/baomidou/mybatis-plus/issues/467">参考链接</a>
     */
    @Override
    @SuppressWarnings("unchecked")
    @Cacheable(cacheNames = CACHE_VALUE_USER, key = "'userList['+#current+':'+#size+':'+#userQueryForm+']'")
    public IPage<User> selectUserPage(Integer current, Integer size, UserQueryForm userQueryForm) {
        String username = userQueryForm.getUsername();
        Page<User> userPage = new Page<>(current, size);
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery()
                .select(User.class, user -> !StringUtils.equals("password", user.getColumn()) &&
                        !StringUtils.equals("update_time", user.getColumn()))
                .eq(StringUtils.isNotEmpty(username), User::getUsername, username);
        // 前端传来的排序数据 example sortMap = {name=update, sort=desc})
        Map<String, String> sortMap = userQueryForm.getSort();
        if (sortMap != null) {
            if (StringUtils.equals(LAST_LOGIN_TIME, sortMap.get(NAME_KEY))) {
                wrapper.orderByAsc(StringUtils.equals(ASC, sortMap.get(SORT_KEY)), User::getLastLoginTime)
                        .orderByDesc(StringUtils.equals(DESC, sortMap.get(SORT_KEY)), User::getLastLoginTime);
            }
        }
        return userMapper.selectPage(userPage, wrapper);
    }

    /**
     * 获取当前用户的角色
     *
     * @return 用户角色数组
     */
    @Override
    @Cacheable(cacheNames = CACHE_VALUE_USER_ROLE, key = "'userRoles['+target.getCurrentusername()+']'")
    public String[] selectUserRole() {
        String currentusername = getCurrentusername();
        User user = userMapper.selectBynameWithRole(currentusername);
        // Role 对象转换 角色名称 数组
        return user.getRoles().stream().map(Role::getName).toArray(String[]::new);
    }

    /**
     * 修改密码
     *
     * @param changePasswordForm 修改密码表单类
     * @param username           用户名
     * @return 是否修改密码成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = CACHE_VALUE_USER, key = "'username['+#username+']'", allEntries = true)
    public Boolean changePassword(ChangePasswordForm changePasswordForm, String username) {
        // 前端传入的密码
        String oldPassword = changePasswordForm.getOldPassword();
        // 前端传入的新密码
        String newPassword = changePasswordForm.getNewPassword();
        // 数据库中的密码
        User user = (User) loadUserByUsername(username);
        String password = user.getPassword();
        // 密码验证
        if (bCryptPasswordEncoder.matches(oldPassword, password)) {
            String encodePassword = bCryptPasswordEncoder.encode(newPassword);
            changePassword(user, username, encodePassword);
            return true;
        }
        return false;
    }

    /**
     * 新建用户
     *
     * @param userForm 用户表单类
     * @return 新建用户的id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer saveUser(UserForm userForm) {
        // 设置加密后的密码
        userForm.setPassword(bCryptPasswordEncoder.encode(userForm.getPassword()));
        // 构建新的User对象
        User user = new User();
        // 复制对象
        BeanUtils.copyProperties(userForm, user);
        // 获取当前用户名
        String currentusername = getCurrentusername();
        user.setCreateBy(currentusername);
        user.setUpdateBy(currentusername);
        user.setEnabled(true);
        return userMapper.insert(user);
    }

    /**
     * 更新用户是否可用
     *
     * @param userForm 用户表单类
     * @return 是否更新成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = CACHE_VALUE_USER, beforeInvocation = true, allEntries = true)
    public Boolean updateUserEnable(UserForm userForm) {
        User user = new User();
        BeanUtils.copyProperties(userForm, user);
        user.setUpdateBy(getCurrentusername());
        // 删除Redis中的Token
        redisServce.removeObject(CACHE_VALUE_TOKEN + user.getId());
        return userMapper.updateById(user) > 0;
    }

    /**
     * 更新用户角色
     *
     * @param userId  用户id
     * @param roleIds 角色id集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @Caching(evict = {
            @CacheEvict(cacheNames = CACHE_VALUE_USER_ROLE, beforeInvocation = true, allEntries = true),
            @CacheEvict(cacheNames = CACHE_VALUE_ROLE, beforeInvocation = true, key = "'roleByUserId['+#userId+']'")
    })
    public void updateUserRole(Integer userId, List<Integer> roleIds) {
        // 先删除对应用户的用户角色表中的数据
        userRoleService.deleteByUserId(userId);
        // 随后插入新的数据
        if (roleIds.size() != 0) {
            userRoleService.insertBatch(userId, roleIds);
        }
    }

    /**
     * 记录用户最后一次登录时间
     *
     * @param user 用户对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = CACHE_VALUE_USER, beforeInvocation = true, allEntries = true)
    public void recordLastLoginTime(User user) {
        user.setLastLoginTime(LocalDateTime.now());
        user.setUpdateBy(user.getUsername());
        userMapper.updateById(user);
    }

    private void changePassword(User user, String username, String password) {
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery()
                .eq(User::getUsername, username);
        user.setPassword(password);
        user.setUpdateBy(getCurrentusername());
        userMapper.update(user, wrapper);
    }

    /**
     * 获取当前用户名，新建个方法是为了 {@link UserServiceImpl#selectUserRole() }
     * 中的key可以获取当前用户名
     *
     * @return 当前用户名
     */
    public String getCurrentusername() {
        return CurrentUserUtils.getCurrentUsername().orElseThrow(NullPointerException::new);
    }

}
