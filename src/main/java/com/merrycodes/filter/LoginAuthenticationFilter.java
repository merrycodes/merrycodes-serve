package com.merrycodes.filter;

import com.merrycodes.config.JwtConfig;
import com.merrycodes.constant.enums.ResponseEnum;
import com.merrycodes.model.entity.Role;
import com.merrycodes.model.entity.User;
import com.merrycodes.model.form.LoginForm;
import com.merrycodes.service.intf.RedisServce;
import com.merrycodes.service.intf.UserService;
import com.merrycodes.utils.JsonUtils;
import com.merrycodes.utils.JwtUtils;
import com.merrycodes.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.merrycodes.constant.consist.CacheValueConsist.CACHE_VALUE_TOKEN;

/**
 * 登录认证过滤器
 * 用户名密码正确，Filte将创建Token
 *
 * @author MerryCodes
 * @date 2020/5/6 20:26
 */
@Slf4j
public class LoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ThreadLocal<Boolean> threadLocal = new ThreadLocal<>();

    private final AuthenticationManager authenticationManager;

    private final RedisServce redisServce;

    private final UserService userService;

    private final JwtConfig jwtConfig;

    public LoginAuthenticationFilter(AuthenticationManager authenticationManager,
                                     RedisServce redisServce, JwtConfig jwtConfig,
                                     UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;
        this.redisServce = redisServce;
        this.userService = userService;
        this.setFilterProcessesUrl(jwtConfig.getAuthLoginUrl());
    }

    /**
     * 登录用户名和密码处理
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            // 表单获取用户名和密码
            LoginForm loginForm = JsonUtils.readValue(request.getInputStream(), LoginForm.class).orElseThrow(NullPointerException::new);
            threadLocal.set(loginForm.getRememberMe());
            User user = new User();
            BeanUtils.copyProperties(loginForm, user);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
            return authenticationManager.authenticate(authenticationToken);
        } catch (IOException e) {
            log.error("获取登录数据出错 msg={}", e.getMessage());
            return null;
        }
    }

    /**
     * 登录认证成功处理
     */
    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                         FilterChain chain, Authentication authResult) throws IOException {
        // 记录用户最后一次登录的时间
        recordLastLoginTime(authResult);
        // 判断用户是否拥有角色，没有则处理
        if (authResult.getAuthorities().size() == 0) {
            String jsonString = JsonUtils.writeValue(ResponseUtils.fail(ResponseEnum.USER_HAS_NO_ROLE)).orElseThrow(NullPointerException::new);
            ResponseUtils.response(response, jsonString);
            return;
        }
        User user = (User) authResult.getPrincipal();
        String token = getTokenInRedis(user.getId());
        if (token == null) {
            // 判断用户是否点击勾选了 "记住我" 是则 Token 有效期为7天，无则有效期为1天
            Long expirationTime = threadLocal.get() ? jwtConfig.getExpirationRemember() : jwtConfig.getExpiration();
            threadLocal.remove();
            token = makeToken(authResult, expirationTime);
            putTokenToRedis(user, token, expirationTime);
        }
        // 给响应头添加 Authorization 附带Token
        response.setHeader(jwtConfig.getTokenHeader(), jwtConfig.getTokenPrefix() + token);
        HashMap<String, String> resultMap = new HashMap<>(1);
        resultMap.put(jwtConfig.getTokenHeader(), jwtConfig.getTokenPrefix() + token);
        // 生成 respond 数据
        String jsonString = JsonUtils.writeValue(ResponseUtils.success(resultMap)).orElseThrow(NullPointerException::new);
        ResponseUtils.response(response, jsonString);
    }

    /**
     * 登录失败处理
     */
    @Override
    public void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                           AuthenticationException failed) throws IOException {
        String jsonString = JsonUtils.writeValue(ResponseUtils.fail(failed.getMessage())).orElseThrow(NullPointerException::new);
        ResponseUtils.response(response, jsonString);
    }

    private String makeToken(Authentication authResult, Long expirationTime) {
        // 就是个类型转换
        List<Role> list = authResult.getAuthorities().stream()
                .map(e -> ((Role) e)).collect(Collectors.toList());
        User user = User.builder().username(authResult.getName()).roles(list).build();
        // 构建新的Token
        return JwtUtils.generateToken(jwtConfig.getJwtPayloadUserKey(), jwtConfig.getPrivateKey(), user, expirationTime);
    }

    private String getTokenInRedis(Integer username) {
        return (String) redisServce.getObject(CACHE_VALUE_TOKEN + username);
    }

    private void putTokenToRedis(User user, String token, Long expirationTime) {
        redisServce.putObject(CACHE_VALUE_TOKEN + user.getId(), token, expirationTime);
    }

    private void recordLastLoginTime(Authentication authResult) {
        User user = (User) authResult.getPrincipal();
        userService.recordLastLoginTime(user);
    }

}
