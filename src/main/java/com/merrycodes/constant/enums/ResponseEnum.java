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
    ILLEGAL_TOKEN(50008, "请重新登录"),

    /**
     * 50016 表示权限不足
     */
    ACCESS_DENIED(50012, "权限不足"),

    /**
     * 50014 用户没有角色
     */
    USER_HAS_NO_ROLE(50016, "用户没有角色,登录失败"),

    /**
     * 50016 表示未登录
     */
    NO_LOGIN_IN(50016, "请登录"),


    /**
     * 50018 表示 Admin 不做修改
     */
    ADMIN_ROLE_NO_CHANGE(50018, "'ADMIN'角色的用户不能修改"),
    ;

    private final Integer code;

    private final String message;

    ResponseEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
