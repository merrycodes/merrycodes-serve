package com.merrycodes.enums;

import lombok.Getter;

/**
 * @author MerryCodes
 * @date 2020/3/31 23:27
 */
@Getter
public enum ArticleEnum {
    /**
     *
     */
//    draft(0,"草稿")
    ;
    private Byte code;

    private String message;

    ArticleEnum(Byte code, String message) {
        this.code = code;
        this.message = message;
    }
}
