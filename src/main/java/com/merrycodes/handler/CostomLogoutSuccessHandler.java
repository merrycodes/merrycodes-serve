package com.merrycodes.handler;

import com.merrycodes.utils.JsonUtils;
import com.merrycodes.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义登出成功处理器
 *
 * @author MerryCodes
 * @date 2020/5/8 16:13
 */
@Slf4j
@Component
public class CostomLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) throws IOException {
        String jsonString = JsonUtils.writeValue(ResponseUtils.success("登出成功")).orElseThrow(NullPointerException::new);
        ResponseUtils.response(response,jsonString);
    }
}
