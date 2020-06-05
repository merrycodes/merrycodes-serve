package com.merrycodes.model.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 修改密码表单类
 *
 * @author MerryCodes
 * @date 2020/5/15 20:38
 */
@Data
@ApiModel(description = "修改密码表单类")
public class ChangePasswordForm {

    /**
     * 旧密码
     */
    @ApiModelProperty("旧密码")
    private String oldPassword;

    /**
     * 新密码
     */
    @ApiModelProperty("新密码")
    private String newPassword;

}
