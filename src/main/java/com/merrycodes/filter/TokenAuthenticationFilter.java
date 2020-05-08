package com.merrycodes.filter;

import com.merrycodes.constant.enums.ResponseEnum;
import com.merrycodes.service.intf.UserService;
import com.merrycodes.utils.JsonUtils;
import com.merrycodes.utils.ResponseUtils;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Token验证过滤器
 *
 * @author MerryCodes
 * @date 2020/5/8 9:48
 */
@Slf4j
public class TokenAuthenticationFilter extends BasicAuthenticationFilter {

    private final UserService userService;

    public TokenAuthenticationFilter(AuthenticationManager authenticationManager, UserService userService) {
        super(authenticationManager);
        this.userService = userService;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader("token");
        if (token == null) {
            // 没有携带token就认为没有登录
            String jsonString = JsonUtils.writeValue(ResponseUtils.fail(ResponseEnum.NOT_LOGIN_IN)).orElseThrow(NullPointerException::new);
            ResponseUtils.response(response, jsonString);

        } else {
            // 携带了正确的token
            UsernamePasswordAuthenticationToken authentication = getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        }
    }

    /**
     * 获取用户认证信息 Authentication
     */
    private UsernamePasswordAuthenticationToken getAuthentication(String username) {
        UserDetails userDetails = userService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
        return userDetails.isEnabled() ? usernamePasswordAuthenticationToken : null;
    }
}
