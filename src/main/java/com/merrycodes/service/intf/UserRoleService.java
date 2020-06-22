package com.merrycodes.service.intf;

import com.baomidou.mybatisplus.extension.service.IService;
import com.merrycodes.model.entity.UserRole;

import java.util.List;

/**
 * 用户角色service接口
 *
 * @author MerryCodes
 * @date 2020/5/5 18:07
 */
public interface UserRoleService extends IService<UserRole> {


    /**
     * 通过用户id查询角色id
     *
     * @param id 用户id
     * @return 用户角色关系类集合
     */
    List<UserRole> selectRoleIdByUserId(Integer id);


    /**
     * 通过用户id删除用户角色关联数据
     *
     * @param id 用户id
     */
    void deleteByUserId(Integer id);

    /**
     * 插入用户角色
     *
     * @param userId 用户id
     * @param roleId 角色id
     */
    void insertOn(Integer userId, Integer roleId);

    /**
     * 批量插入用户角色
     *
     * @param userId  用户id
     * @param roleIds 角色id数组
     */
    void insertBatch(Integer userId, List<Integer> roleIds);

    /**
     * 查看用户是否有 ADMIN权限
     *
     * @param userId 用户id
     * @return {@link Boolean}
     */
    Boolean checkAdminRole(Integer userId);

}
