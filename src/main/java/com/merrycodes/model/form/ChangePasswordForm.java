package com.merrycodes.model.form;

import lombok.Data;

/**
 * 修改密码表单类
 *
 * @author MerryCodes
 * @date 2020/5/15 20:38
 */
@Data
public class ChangePasswordForm {

    /**
     * 旧密码
     */
    private String oldPassword;

    /**
     * 新密码
     */
    private String newPassword;

}
