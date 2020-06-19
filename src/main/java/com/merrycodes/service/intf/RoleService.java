package com.merrycodes.service.intf;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.merrycodes.model.entity.Role;
import com.merrycodes.model.form.query.RoleQueryForm;
import com.merrycodes.model.vo.RoleVo;

import java.util.List;

/**
 * 角色service接口
 *
 * @author MerryCodes
 * @date 2020/5/5 18:04
 */
public interface RoleService extends IService<Role> {

    /**
     * 角色分页查询 (分页)
     *
     * @param current       当前页数
     * @param size          当前分页总页数
     * @param roleQueryForm 角色查询表当类
     * @return 分页 Page 对象接口 {@link IPage}
     */
    IPage<RoleVo> selectRolePage(Integer current, Integer size, RoleQueryForm roleQueryForm);

    /**
     * 根据角色名称和描述判断是否存在角色
     *
     * @param name        角色名
     * @param description 角色描述
     * @return {@link Boolean} 存在 / 不存在 false / true
     */
    Boolean exits(String name, String description);

    /**
     * 根据角色名查询角色
     *
     * @param name 角色名
     * @return {@link Role}
     */
    Role selectByName(String name);

    /**
     * 根据用户id查询用户的角色
     *
     * @param id 用户id
     * @return 角色集合
     */
    List<Role> selectRoleByUserId(Integer id);

    /**
     * 查询角色的名字和描述
     *
     * @return 角色集合
     */
    List<Role> selectRoleWithNameDescription();

}
