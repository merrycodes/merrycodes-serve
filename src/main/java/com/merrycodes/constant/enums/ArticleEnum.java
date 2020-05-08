package com.merrycodes.constant.enums;

import com.merrycodes.constant.enums.intf.CodeEnum;
import lombok.Getter;

/**
 * 文章状态枚举
 *
 * @author MerryCodes
 * @date 2020/3/31 23:27
 */
@Getter
public enum ArticleEnum implements CodeEnum {
    /**
     * 0 表示文章状态为草稿
     */
    DRAFT(0, "草稿"),

    /**
     * 1 表示文章状态为发布
     */
    PUBLISH(1, "已发布"),

    /**
     * 2 表示文章状态为取消发布
     */
    CANCEL_PUBLISH(2, "取消发布"),
    ;
    private final Integer code;

    private final String message;

    ArticleEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
