package com.merrycodes.enums;

import lombok.Getter;

/**
 * 文章标签枚举类
 *
 * @author MerryCodes
 * @date 2020/4/12 23:40
 */
@Getter
public enum TagsEnum implements CodeEnum {
    /**
     * 0 表示文章标签状态为失效
     */
    UNVALID(0, "失效"),

    /**
     * 1 表示文章标签状态为生效
     */
    VALID(1, "生效"),
    ;

    private Integer code;

    private String message;

    TagsEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
