package com.merrycodes.service.intf;

import com.baomidou.mybatisplus.extension.service.IService;
import com.merrycodes.model.entity.Role;

/**
 * 角色service接口
 *
 * @author MerryCodes
 * @date 2020/5/5 18:04
 */
public interface RoleService extends IService<Role> {

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
    Role findByName(String name);


}
