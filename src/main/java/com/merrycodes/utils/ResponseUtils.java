package com.merrycodes.utils;

import com.merrycodes.constant.enums.ResponseEnum;
import com.merrycodes.model.vo.ResponseVo;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 统一返回前端对象工具类
 *
 * @author MerryCodes
 * @date 2020/3/31 22:47
 */
public class ResponseUtils {

    public static <T> ResponseVo<T> success() {
        return new ResponseVo<>(ResponseEnum.SUCCESS.getCode());
    }

    public static <T> ResponseVo<T> success(String message) {
        return new ResponseVo<>(ResponseEnum.SUCCESS.getCode(), message);
    }

    public static <T> ResponseVo<T> success(T data) {
        return new ResponseVo<>(ResponseEnum.SUCCESS.getCode(), data);
    }

    public static <T> ResponseVo<T> success(String message, T data) {
        return new ResponseVo<>(ResponseEnum.SUCCESS.getCode(), message, data);
    }

    public static <T> ResponseVo<T> fail() {
        return new ResponseVo<>(ResponseEnum.FAIL.getCode());
    }

    public static <T> ResponseVo<T> fail(String message) {
        return new ResponseVo<>(ResponseEnum.FAIL.getCode(), message);
    }

    public static <T> ResponseVo<T> fail(ResponseEnum responseEnum) {
        return new ResponseVo<>(responseEnum.getCode(), responseEnum.getMessage());
    }

    public static void response(ServletResponse response, String string) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        @Cleanup PrintWriter writer = response.getWriter();
        writer.println(string);
    }
}
