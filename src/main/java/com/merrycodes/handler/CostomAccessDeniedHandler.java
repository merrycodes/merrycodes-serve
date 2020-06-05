package com.merrycodes.handler;

import com.merrycodes.constant.enums.ResponseEnum;
import com.merrycodes.utils.JsonUtils;
import com.merrycodes.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.ExceptionTranslationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 权限处理器
 * 处理 {@link AccessDeniedException} 异常可以查看 {@link ExceptionTranslationFilter}
 *
 * @author MerryCodes
 * @date 2020/5/16 9:48
 */
@Slf4j
public class CostomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        log.error("【权限出错，msg={}】", accessDeniedException.getMessage());
        String jsonString = JsonUtils.writeValue(ResponseUtils.fail(ResponseEnum.ACCESS_DENIED)).orElseThrow(NullPointerException::new);
        ResponseUtils.response(response, jsonString);
    }
}
