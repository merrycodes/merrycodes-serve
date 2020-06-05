package com.merrycodes.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.merrycodes.model.entity.abstracts.AbstractSecurityEntiry;
import com.merrycodes.utils.ToStringStyleUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Tolerate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.security.core.GrantedAuthority;

/**
 * 角色实体类
 *
 * @author MerryCodes
 * @date 2020/5/4 17:20
 */
@Data
@Builder
@ApiModel(description = "角色实体类")
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Role extends AbstractSecurityEntiry implements GrantedAuthority {

    private static final long serialVersionUID = -8373262132842412137L;
    /**
     * 角色id
     */
    @ApiModelProperty("角色id")
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 角色名称
     */
    @ApiModelProperty("角色名称")
    private String name;

    /**
     * 角色描述
     */
    @ApiModelProperty("角色描述")
    private String description;

    /**
     * 角色有效用户数
     */
    @TableField(exist = false)
    @ApiModelProperty("角色有效用户数")
    private Integer count;

    @Tolerate
    public Role() {
    }

    @Override
    @JsonIgnore
    public String getAuthority() {
        return getName();
    }

    @JsonIgnore
    public static Role of(String name, String description) {
        return Role.builder().name(name).description(description).build();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyleUtils.NO_NULL_STYLE);
    }
}
