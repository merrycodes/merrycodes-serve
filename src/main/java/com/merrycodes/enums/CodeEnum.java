package com.merrycodes.enums;

import lombok.Getter;

/**
 * 响应编码枚举
 *
 * @author MerryCodes
 * @date 2020/3/31 22:54
 */
@Getter
public enum CodeEnum {

    /**
     * 200 表示请求成功
     */
    SUCCESS(200, "succuss"),

    /**
     * -1 表示请求成功
     */
    FAIL(400, "fail"),

    ;

    private Integer code;

    private String message;

    CodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
