package com.merrycodes.filter;

import com.merrycodes.config.JwtConfig;
import com.merrycodes.model.entity.Role;
import com.merrycodes.model.entity.User;
import com.merrycodes.utils.JsonUtils;
import com.merrycodes.utils.JwtUtils;
import com.merrycodes.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 登录认证过滤器
 * 用户名密码正确，Filte将创建Token
 *
 * @author MerryCodes
 * @date 2020/5/6 20:26
 */
@Slf4j
public class LoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final JwtConfig jwtConfig;

    public LoginAuthenticationFilter(AuthenticationManager authenticationManager, JwtConfig jwtConfig) {
        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;
        this.setFilterProcessesUrl(jwtConfig.getAuthLoginUrl());
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            // TODO: MerryCodes 2020-05-12 09:22:10 remember-me
            User user = JsonUtils.readValue(request.getInputStream(), User.class).orElseThrow(NullPointerException::new);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
            return authenticationManager.authenticate(authenticationToken);
        } catch (IOException e) {
            log.error("获取登录数据出错 msg={}", e.getMessage());
            return null;
        }
    }

    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                         FilterChain chain, Authentication authResult) throws IOException {
        List<Role> list = authResult.getAuthorities()
                .stream()
                .map(e -> ((Role) e))
                .collect(Collectors.toList());
        User user = User.builder().username(authResult.getName()).roles(list).build();
        // 默认为一天
        String token = JwtUtils.generateToken(jwtConfig.getJwtPayloadUserKey(), jwtConfig.getPrivateKey(), user, jwtConfig.getExpiration());
        response.setHeader(jwtConfig.getTokenHeader(), jwtConfig.getTokenPrefix() + token);
        HashMap<String, String> resultMap = new HashMap<>(1);
        resultMap.put(jwtConfig.getTokenHeader(), jwtConfig.getTokenPrefix() + token);
        String jsonString = JsonUtils.writeValue(ResponseUtils.success(resultMap)).orElseThrow(NullPointerException::new);
        ResponseUtils.response(response, jsonString);
    }

    @Override
    public void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                           AuthenticationException failed) throws IOException {
        String jsonString = JsonUtils.writeValue(ResponseUtils.fail(failed.getMessage())).orElseThrow(NullPointerException::new);
        ResponseUtils.response(response, jsonString);
    }

}
