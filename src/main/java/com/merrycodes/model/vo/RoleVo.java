package com.merrycodes.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.merrycodes.model.entity.User;
import com.merrycodes.utils.ToStringStyleUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * 角色对象模型
 *
 * @author MerryCodes
 * @date 2020/5/30 17:20
 */
@Data
@ApiModel(description = "角色对象模型")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleVo implements Serializable {

    private static final long serialVersionUID = 3691164423526213863L;

    /**
     * 角色id
     */
    @ApiModelProperty("角色id")
    private Integer id;

    /**
     * 角色名
     */
    @ApiModelProperty("角色名")
    private String name;

    /**
     * 角色描述
     */
    @ApiModelProperty("角色描述")
    private String description;

    /**
     * 用户实体类集合
     *
     * @see User
     */
    @ApiModelProperty("用户实体类集合")
    private List<User> userList;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyleUtils.NO_NULL_STYLE);
    }
}
