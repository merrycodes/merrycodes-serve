package com.merrycodes.util;

import com.merrycodes.enums.ResponseEnum;
import com.merrycodes.vo.ResponseVo;

/**
 * 统一返回前端对象工具类
 *
 * @author MerryCodes
 * @date 2020/3/31 22:47
 */
public class ResponseUtil {

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
}
