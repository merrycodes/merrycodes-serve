package com.merrycodes.model.form;

import com.merrycodes.utils.ToStringStyleUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 用户表单类
 *
 * @author MerryCodes
 * @date 2020/5/21 22:42
 */
@Data
@ApiModel(description = "用户表单类")
public class UserForm {

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private Integer id;

    /**
     * 用户是否可用
     */
    @ApiModelProperty("用户是否可用")
    private Boolean enabled;

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String username;

    /**
     * 用户密码
     */
    @ApiModelProperty("用户密码")
    private String password;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyleUtils.NO_NULL_STYLE);
    }

}
