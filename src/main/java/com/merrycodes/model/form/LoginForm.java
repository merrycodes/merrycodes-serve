package com.merrycodes.model.form;

import lombok.Data;

/**
 * 用户登录表单
 *
 * @author MerryCodes
 * @date 2020/5/13 18:57
 */
@Data
public class LoginForm {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;
}
