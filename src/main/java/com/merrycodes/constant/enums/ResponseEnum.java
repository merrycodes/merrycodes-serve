package com.merrycodes.constant.enums;

import com.merrycodes.constant.enums.intf.CodeEnum;
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

    /**
     * 50016 表示权限不足
     */
    ACCESS_DENIED(50016, "权限不足"),

    /**
     * 50016 表示未登录
     */
    NOT_LOGIN_IN(50018, "请登录"),
    ;

    private final Integer code;

    private final String message;

    ResponseEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
