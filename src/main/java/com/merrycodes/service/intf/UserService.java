package com.merrycodes.service.intf;

import com.baomidou.mybatisplus.extension.service.IService;
import com.merrycodes.model.entity.User;
import com.merrycodes.model.form.ChangePasswordForm;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * 用户service接口
 *
 * @author MerryCodes
 * @date 2020/5/4 18:20
 */
public interface UserService extends IService<User>, UserDetailsService {

    /**
     * 获取当前用户的角色
     *
     * @return 用户的数组
     */
    String[] selectUserRole();


    /**
     * 修改密码
     *
     * @param changePasswordForm 修改密码表单类
     * @param username           用户名
     * @return 是否修改密码成功
     */
    Boolean changePassword(ChangePasswordForm changePasswordForm, String username);

}
