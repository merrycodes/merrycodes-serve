package com.merrycodes.enums;

import lombok.Getter;

/**
 * 响应编码枚举
 *
 * @author MerryCodes
 * @date 2020/3/31 22:54
 */
@Getter
public enum ResponseEnum implements CodeEnum {

    /**
     * 20000 表示请求成功
     */
    SUCCESS(20000, "成功"),

    /**
     * 50004 表示请求失败
     */
    FAIL(50004, "失败"),

    /**
     * 50008 表示非法的token
     */
    ILLEGAL_TOKEN(50008, "非法的token"),

    /**
     * 50012 表示其他客户端登陆（单点登陆）
     */
    OTHER_CILENT_LOGIN(50012, "其他客户端登录"),

    /**
     * 50014 表示token过期
     */
    TOKEN_EXPIRE(50014, "token过期"),

    ;

    private Integer code;

    private String message;

    ResponseEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
