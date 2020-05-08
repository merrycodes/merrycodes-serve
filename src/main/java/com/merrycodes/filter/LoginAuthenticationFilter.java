package com.merrycodes.filter;

import com.merrycodes.constant.consist.SecurityConsist;
import com.merrycodes.model.entity.User;
import com.merrycodes.utils.JsonUtils;
import com.merrycodes.utils.ResponseUtils;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录认证过滤器
 *
 * @author MerryCodes
 * @date 2020/5/6 20:26
 */
@Slf4j
public class LoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public LoginAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.setFilterProcessesUrl(SecurityConsist.AUTH_LOGIN_URL);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
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
        response.setHeader("token", authResult.getName());
        HashMap<String, String> resultMap = new HashMap<>(1);
        resultMap.put("token", authResult.getName());
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
