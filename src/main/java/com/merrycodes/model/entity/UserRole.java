package com.merrycodes.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.merrycodes.model.entity.abstracts.AbstractSecurityEntiry;
import lombok.*;
import lombok.experimental.Tolerate;

/**
 * 用户角色实体类
 *
 * @author MerryCodes
 * @date 2020/5/5 12:05
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class UserRole extends AbstractSecurityEntiry {

    /**
     * 用户角色id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 角色id
     */
    private Integer roleId;

    @Tolerate
    public UserRole() {
    }

    public static UserRole of(Integer userId, Integer roleId) {
        return UserRole.builder().userId(userId).roleId(roleId).build();
    }

}
