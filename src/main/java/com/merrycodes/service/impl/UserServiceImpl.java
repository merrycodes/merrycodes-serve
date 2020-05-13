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

    @Override
    @Cacheable(cacheNames = CACHE_VALUE_USER, key = "'username['+#username+']'")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectBynameWithRole(username);
        if (user == null) {
            throw new UsernameNotFoundException("");
        }
        return user;
    }

    @Override
    @Cacheable(cacheNames = CACHE_VALUE_USER, key = "'userRoles['+target.getCurrentusername()+']'")
    public String[] selectUserRole() {
        String currentusername = getCurrentusername();
        User user = userMapper.selectBynameWithRole(currentusername);
        return user.getRoles().stream().map(Role::getName).toArray(String[]::new);
    }

    public String getCurrentusername() {
        return CurrentUserUtils.getCurrentUsername().orElseThrow(NullPointerException::new);
    }
}
