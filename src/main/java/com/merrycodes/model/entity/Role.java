package com.merrycodes.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.merrycodes.model.entity.abstracts.AbstractSecurityEntiry;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Tolerate;
import org.springframework.security.core.GrantedAuthority;

/**
 * 角色实体类
 *
 * @author MerryCodes
 * @date 2020/5/4 17:20
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class Role extends AbstractSecurityEntiry implements GrantedAuthority {

    /**
     * 角色id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色描述
     */
    private String description;

    @Tolerate
    public Role() {
    }

    @Override
    @JsonIgnore
    public String getAuthority() {
        return getName();
    }

    public static Role of(String name, String description) {
        return Role.builder().name(name).description(description).build();
    }
}
