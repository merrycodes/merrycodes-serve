package com.merrycodes.constant.enums;

import lombok.Getter;
import org.springframework.stereotype.Component;

/**
 * 角色枚举类
 *
 * @author MerryCodes
 * @date 2020/5/5 16:28
 */
@Getter
public enum RoleTypeEnum {

    /**
     * 普通用户
     */
    USER("USER", "普通用户"),

    /**
     * 管理员
     */
    ADMIN("ADMIN", "管理员"),
    ;

    private final String name;

    private final String description;

    RoleTypeEnum(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
