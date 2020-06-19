package com.merrycodes.handler;

import com.merrycodes.constant.enums.ResponseEnum;
import com.merrycodes.utils.JsonUtils;
import com.merrycodes.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.ExceptionTranslationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证处理器
 * 处理 {@link AuthenticationException} 异常可以查看 {@link ExceptionTranslationFilter}
 *
 * @author MerryCodes
 * @date 2020/5/16 11:01
 */
@Slf4j
public class CostomAuthenticationEntryPointHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        // 没有认证信息（未登录且没有携带合法的Token）
        if (authException instanceof InsufficientAuthenticationException) {
            log.error("【认证异常】msg={}", authException.getMessage());
            String jsonString = JsonUtils.writeValue(ResponseUtils.fail(ResponseEnum.ILLEGAL_TOKEN)).orElseThrow(NullPointerException::new);
            ResponseUtils.response(response, jsonString);
        }
    }
}
