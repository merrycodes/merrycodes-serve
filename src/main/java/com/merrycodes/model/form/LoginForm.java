package com.merrycodes.model.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户登录表单类
 *
 * @author MerryCodes
 * @date 2020/5/13 18:57
 */
@Data
@ApiModel(description = "用户登录表单类")
public class LoginForm {

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

    /**
     * 记住我
     */
    @ApiModelProperty("记住我")
    private Boolean rememberMe;

}
