package com.merrycodes.service.intf;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.merrycodes.model.entity.User;
import com.merrycodes.model.form.ChangePasswordForm;
import com.merrycodes.model.form.UserForm;
import com.merrycodes.model.form.query.UserQueryForm;
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
     * 用户分页查询
     *
     * @param current       当前页数
     * @param size          当前分页总页数
     * @param userQueryForm 用户查询表单类
     * @return 分页 Page 对象接口 {@link IPage}
     */
    IPage<User> selectUserPage(Integer current, Integer size, UserQueryForm userQueryForm);

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

    /**
     * 新建用户
     *
     * @param userForm 用户表单类
     * @return 新建用户的id
     */
    Integer saveUser(UserForm userForm);

    /**
     * 更新用户是否可用
     *
     * @param userForm 用户表单类
     * @return 是否更新成功
     */
    Boolean updateUserEnable(UserForm userForm);

    /**
     * 更新用户角色
     *
     * @param userId  用户id
     * @param roleIds 角色id集合
     */
    void updateUserRole(Integer userId, List<Integer> roleIds);

    /**
     * 记录用户最后一次登录时间
     *
     * @param user 用户对象
     */
    void recordLastLoginTime(User user);

}
