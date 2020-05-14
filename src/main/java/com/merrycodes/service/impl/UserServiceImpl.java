package com.merrycodes.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.merrycodes.mapper.UserMapper;
import com.merrycodes.model.entity.Role;
import com.merrycodes.model.entity.User;
import com.merrycodes.service.intf.UserService;
import com.merrycodes.utils.CurrentUserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.merrycodes.constant.consist.CacheValueConsist.CACHE_VALUE_USER;

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

    /**
     * Spring Security 用户登录验证的方法
     *
     * @param username 用户名
     * @return {@link UserDetails}
     * @throws UsernameNotFoundException 用户名不存在异常
     */
    @Override
    @Cacheable(cacheNames = CACHE_VALUE_USER, key = "'username['+#username+']'")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectBynameWithRole(username);
        if (user == null) {
            throw new UsernameNotFoundException("");
        }
        return user;
    }

    /**
     * 获取当前用户的角色
     *
     * @return 用户角色数组
     */
    @Override
    @Cacheable(cacheNames = CACHE_VALUE_USER, key = "'userRoles['+target.getCurrentusername()+']'")
    public String[] selectUserRole() {
        String currentusername = getCurrentusername();
        User user = userMapper.selectBynameWithRole(currentusername);
        // Role 对象转换 角色名称 数组
        return user.getRoles().stream().map(Role::getName).toArray(String[]::new);
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
