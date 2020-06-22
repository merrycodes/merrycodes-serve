package com.merrycodes.handler.global;

import com.merrycodes.constant.enums.ResponseEnum;
import com.merrycodes.handler.global.strategy.GlobalExceptionContext;
import com.merrycodes.model.vo.ResponseVo;
import com.merrycodes.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常拦截器
 *
 * @author MerryCodes
 * @date 2020/6/10 15:53
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseVo<String> exceptionHandler(Exception exception, HttpServletRequest request) {
        if (exception instanceof DuplicateKeyException) {
            return duplicateKeyExceptionHandler(exception, request);
        } else if (exception instanceof AccessDeniedException) {
            return costomAccessDeniedHandler(exception);
        }
        log.error("【运行时错误】msg={}", exception.toString());
        return ResponseUtils.fail("服务器错误");
    }

    public ResponseVo<String> duplicateKeyExceptionHandler(Exception exception, HttpServletRequest request) {
        log.error("【唯一索引】 msg={}", exception.getMessage());
        String servletPath = request.getServletPath();
        String flag = servletPath.substring(servletPath.lastIndexOf("/") + 1);
        // 可以使用 “ 策略模式 ”
//        String errorMessage = null;
//        switch (flag) {
//            case "tags":
//                errorMessage = "标签名已经存在，新建失败";
//                break;
//            case "category":
//                errorMessage = "分类名已经存在，新建失败";
//                break;
//            case "article":
//                errorMessage = "文章标题已经存在，新建失败";
//                break;
//            default:
//                break;
//        }
        // 为了用策略模式而用策略模式 =.=
        String errorMessage = GlobalExceptionContext.getInstance().getStrategy(flag).getMessage();
        return ResponseUtils.fail(errorMessage);
    }

    public ResponseVo<String> costomAccessDeniedHandler(Exception exception) {
        log.error("【权限出错】msg={}", exception.getMessage());
        return ResponseUtils.fail(ResponseEnum.ACCESS_DENIED);
    }

}
