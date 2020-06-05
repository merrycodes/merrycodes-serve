package com.merrycodes.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.merrycodes.model.entity.abstracts.AbstractSecurityEntiry;
import com.merrycodes.utils.ToStringStyleUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Tolerate;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 用户角色实体类
 *
 * @author MerryCodes
 * @date 2020/5/5 12:05
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "用户角色实体类")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRole extends AbstractSecurityEntiry {

    private static final long serialVersionUID = -3935509846530346441L;
    /**
     * 用户角色id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("用户角色id")
    private Integer id;

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private Integer userId;

    /**
     * 角色id
     */
    @ApiModelProperty("角色id")
    private Integer roleId;

    @Tolerate
    public UserRole() {
    }

    @JsonIgnore
    public static UserRole of(Integer userId, Integer roleId) {
        return UserRole.builder().userId(userId).roleId(roleId).build();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyleUtils.NO_NULL_STYLE);
    }
}
