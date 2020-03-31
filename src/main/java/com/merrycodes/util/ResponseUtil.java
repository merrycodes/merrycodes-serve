package com.merrycodes.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.merrycodes.enums.CodeEnum;
import lombok.Getter;

/**
 * 统一返回前端对象
 *
 * @author MerryCodes
 * @date 2020/3/31 22:47
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseUtil<T> {

    private Integer code;

    private String message;

    private T data;

    private ResponseUtil(Integer code) {
        this.code = code;
    }

    private ResponseUtil(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private ResponseUtil(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    private ResponseUtil(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ResponseUtil<T> success() {
        return new ResponseUtil<>(CodeEnum.SUCCESS.getCode());
    }

    public static <T> ResponseUtil<T> success(String message) {
        return new ResponseUtil<>(CodeEnum.SUCCESS.getCode(), message);
    }

    public static <T> ResponseUtil<T> success(T data) {
        return new ResponseUtil<>(CodeEnum.SUCCESS.getCode(), data);
    }


    public static <T> ResponseUtil<T> success(String message, T data) {
        return new ResponseUtil<>(CodeEnum.SUCCESS.getCode(), message, data);
    }

    public static <T> ResponseUtil<T> fail() {
        return new ResponseUtil<>(CodeEnum.FAIL.getCode());
    }

    public static <T> ResponseUtil<T> fail(String message) {
        return new ResponseUtil<>(CodeEnum.FAIL.getCode(), message);
    }
}
