package com.merrycodes.enums;

import lombok.Getter;

/**
 * 文章标签 / 文章分类 状态枚举类
 *
 * @author MerryCodes
 * @date 2020/4/12 23:40
 */
@Getter
public enum StatusEnum implements CodeEnum {
    /**
     * 0 表示状态为失效
     */
    UNVALID(0, "失效"),

    /**
     * 1 表示状态为生效
     */
    VALID(1, "生效"),
    ;

    private Integer code;

    private String message;

    StatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
