package com.merrycodes.filter;

import com.merrycodes.config.JwtConfig;
import com.merrycodes.constant.enums.ResponseEnum;
import com.merrycodes.exception.TokenException;
import com.merrycodes.model.entity.JwtPayload;
import com.merrycodes.model.entity.User;
import com.merrycodes.service.intf.UserService;
import com.merrycodes.utils.JsonUtils;
import com.merrycodes.utils.JwtUtils;
import com.merrycodes.utils.ResponseUtils;
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

/**
 * Token验证过滤器
 * Filter处理所有HTTP请求，检查Header中是否携带着Token，并检查Token是否正确/过期
 *
 * @author MerryCodes
 * @date 2020/5/8 9:48
 */
@Slf4j
public class TokenAuthenticationFilter extends BasicAuthenticationFilter {

    private final UserService userService;

    private final JwtConfig jwtConfig;

    public TokenAuthenticationFilter(AuthenticationManager authenticationManager, UserService userService, JwtConfig jwtConfig) {
        super(authenticationManager);
        this.userService = userService;
        this.jwtConfig = jwtConfig;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(jwtConfig.getTokenHeader());
        // 是否正确的携带了Token
        if (header != null && header.startsWith(jwtConfig.getTokenPrefix())) {
            UsernamePasswordAuthenticationToken authentication;
            try {
                authentication = getAuthentication(header);
                // 携带了错误的Token，会抛出异常
            } catch (TokenException e) {
                log.error("Token 解析错误 msg={}", e.getMessage());
                String jsonString = JsonUtils.writeValue(ResponseUtils.fail(ResponseEnum.ILLEGAL_TOKEN)).orElseThrow(NullPointerException::new);
                ResponseUtils.response(response, jsonString);
                return;
            }
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }

    /**
     * 验证是否携带正确的Token
     *
     * @param header 请求头
     * @return {@link UsernamePasswordAuthenticationToken}
     */
    private UsernamePasswordAuthenticationToken getAuthentication(String header) throws TokenException {
        // 获取Token
        String token = header.replace(jwtConfig.getTokenPrefix(), "");
        // 解析Token
        JwtPayload jwtPayload = JwtUtils.getUserFromToken(token, jwtConfig.getJwtPayloadUserKey(), jwtConfig.getPublicKey());
        User user = jwtPayload.getUser();
        if (user != null) {
            // 查询数据库，避免用户的角色发生改变
            UserDetails userDetails = userService.loadUserByUsername(user.getUsername());
            return userDetails.isEnabled() ? new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getRoles()) : null;
        }
        return null;
    }
}
